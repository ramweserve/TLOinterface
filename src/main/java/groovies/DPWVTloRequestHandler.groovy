import com.navis.argo.ArgoField
import com.navis.argo.ContextHelper
import com.navis.argo.business.api.ArgoUtils
import com.navis.argo.business.atoms.*
import com.navis.carina.integrationservice.business.IntegrationService
import com.navis.external.argo.AbstractJmsCodeExtension
import com.navis.external.framework.util.ExtensionUtils
import com.navis.framework.business.atoms.IntegrationServiceDirectionEnum
import com.navis.framework.metafields.MetafieldId
import com.navis.framework.metafields.MetafieldIdFactory
import com.navis.framework.persistence.HibernateApi
import com.navis.framework.portal.QueryUtils
import com.navis.framework.portal.query.DomainQuery
import com.navis.framework.portal.query.PredicateFactory
import com.navis.inventory.InventoryField
import com.navis.inventory.MovesField
import com.navis.inventory.business.atoms.UfvTransitStateEnum
import com.navis.inventory.business.moves.WorkInstruction
import com.navis.inventory.business.units.UnitFacilityVisit
import com.navis.rail.RailField
import com.navis.rail.business.atoms.SpottingStatusEnum
import com.navis.rail.business.atoms.TrainDirectionEnum
import com.navis.rail.business.entity.Railcar
import com.navis.rail.business.entity.RailcarType
import com.navis.rail.business.entity.TrainVisitDetails
import com.syncrotess.external.n4_tlo.common.types.RequestMessage
import org.apache.log4j.Level
import org.apache.log4j.Logger

class DPWVTloRequestHandler extends AbstractJmsCodeExtension {

    void execute(Object inPayload, Map<String, Object> inProperties) {
        LOGGER.setLevel(Level.DEBUG);
        LOGGER.debug("inPayload: " + inPayload + ", inProperties: " + inProperties);
        handleRequest(inPayload, inProperties);
    }

    private String handleRequest(String inXmlMessage, Map<String, Object> inProperties) {
        try {
            String responseXmlMessage;
            if (ArgoUtils.isEmpty(inXmlMessage)) {
                LOGGER.error("Payload message is empty");
                return;
            }

            String requestResponseId;
            if (adaptor) {
                try {
                    String destination = inProperties.get(adaptor.TEXT_JMS_DESTINATION);
                    if (destination) {
                        String intServName = destination.substring(destination.indexOf("://") + 3);
                        IntegrationService iServ = adaptor.getUriFromIntegrationService(intServName, IntegrationServiceDirectionEnum.INDOUND);
                        adaptor.createIntegrationSrcMsg(null, null, iServ, inXmlMessage);
                    }
                } catch (Exception msgException) {
                    LOGGER.error("Error while recording inbound message : " + msgException.getMessage());
                }
                if(inXmlMessage.contains(TAG_COMMON_HEADER)) {
                    RequestMessage requestMessage = (RequestMessage) adaptor.xmlToJaxb(Class.forName(adaptor.REQUEST_MESSAGE_CLASS), inXmlMessage)
                    if (requestMessage) {
                        requestResponseId = requestMessage.getHeader() ? requestMessage.getHeader().getRequestResponseId() : "-1";
                        LOGGER.debug("requestResponseId: " + requestResponseId);
                    }
                }
            }


            if (inXmlMessage.contains(TEXT_REQUEST_RAILCARTYPE)) {
                LOGGER.debug("create RailCarTypeMessageProcessor");
                List<RailcarType> RailcarTypeList = getReferenceDataForEntity(TEXT_RAILCARTYPE);
                responseXmlMessage = processRequest(requestResponseId, HANDLER_RAILCAR_TYPE, RailcarTypeList);

            } else if (inXmlMessage.contains(TEXT_REQUEST_RAILCAR)) {
                List<Railcar> railcarList = getReferenceDataForEntity(TEXT_RAILCAR);
                responseXmlMessage = processRequest(requestResponseId, HANDLER_RAILCAR, railcarList)

            } else if (inXmlMessage.contains(TEXT_REQUEST_CONTAINER)) {
                List<UnitFacilityVisit> containerList = getReferenceDataForEntity(TEXT_CONTAINER);
                responseXmlMessage = processRequest(requestResponseId, HANDLER_CONTAINER, containerList)

            } else if (inXmlMessage.contains(TEXT_REQUEST_CONTAINERTRANSIT)) {
                List<WorkInstruction> workInstructionList = getReferenceDataForEntity(TEXT_WI);
                //LOGGER.debug("workInstructionList: " + workInstructionList);
                responseXmlMessage = processRequest(requestResponseId, HANDLER_CONTAINERTRANSIT, workInstructionList)

            } else if (inXmlMessage.contains(TEXT_REQUEST_SUBTRAIN)) {
                List<TrainVisitDetails> trainVisitList = getReferenceDataForEntity(TEXT_TRAIN_VISIT);
                LOGGER.debug("trainVisitList: " + trainVisitList);
                responseXmlMessage = processRequest(requestResponseId, HANDLER_SUBTRAIN, trainVisitList)

                /* } else if (inXmlMessage.contains(TEXT_REQUEST_PING)) {
                     responseXmlMessage = processPing(requestResponseId)*/

            } else {
                responseXmlMessage = "From N4: No matching request";
            }
            LOGGER.debug("responseXmlMessage: " + responseXmlMessage);
            if (adaptor)
                adaptor.pushOutboundJmsMessage(responseXmlMessage, adaptor.INTEGRATION_SERVICE__N4TOTLO);
            //For test
            //throw new NullPointerException("custom error");
        } catch (Exception e) {
            LOGGER.error("Exception in handleRequest : " + e);
            adaptor.recordIntegrationError(e, null, null, IntegrationTypeEnum.JMS_OUTBOUND_TEXT, IntegrationActionStatusEnum.ERROR, null, adaptor.BUSINESS_KEY__TLO_INTERFACE);
        }
    }

    private String processRequest(String inRequestResponseId, String handlerName, List entityList) {
        def messageHandler = ExtensionUtils.getLibrary(ContextHelper.getThreadUserContext(), handlerName);
        LOGGER.debug(handlerName + " - messageHandler: " + messageHandler);
        return messageHandler.processRequest(entityList, adaptor.buildHeader(inRequestResponseId), adaptor);
    }

    /*private String processPing(String inRequestResponseId) {
        PingResponse pingResponse = new PingResponse();
        pingResponse.setHeader(adaptor.buildHeader(inRequestResponseId));
        return adaptor.jaxbToXml(Class.forName(adaptor.PING_RESPONSE_CLASS), pingResponse);
    }*/

    private List getReferenceDataForEntity(String entityName) {
        DomainQuery dq;

        if (TEXT_RAILCARTYPE.equals(entityName)) { //RailcarType
            dq = QueryUtils.createDomainQuery(entityName);

        } else if (TEXT_RAILCAR.equals(entityName)) { //Railcar
            dq = QueryUtils.createDomainQuery("RailcarVisit")
                    .addDqPredicate(PredicateFactory.eq(RailField.RCARV_FACILITY, ContextHelper.getThreadFacility().getFcyGkey()))
                    .addDqPredicate(PredicateFactory.eq(RailField.RCARV_SPOTTING_STATUS, SpottingStatusEnum.MARK));
            //.addDqPredicate(PredicateFactory.eq(RailField.RCARV_IS_ACTIVE, Boolean.TRUE))
            //.addDqField(RailField.RCARV_RAILCAR);
            dq.setScopingEnabled(false);

        } else if (TEXT_CONTAINER.equals(entityName)) { //container
            //@TODO: Do we need to include units associated with IB Train
            //Find active units associated with OB Train
            dq = getActiveUnitsAssociatedWithTrain();

        } else if (TEXT_WI.equals(entityName)) {
            //Container transit //@TODO: to be tested in client test env, as having issue in local xps
            dq = QueryUtils.createDomainQuery("WorkInstruction")
                    .addDqPredicate(PredicateFactory.in(MovesField.WI_MOVE_STAGE, ALLOWED_WI_MOVE_STAGE))
                    .addDqPredicate(PredicateFactory.in(MovesField.WI_MOVE_KIND, ALLOWED_WI_MOVE_KIND));

        } else if (TEXT_TRAIN_VISIT.equals(entityName)) { //sub train
            //Only outbound
            //visit phase = created, Inbound, Arrived, Working
            //MetafieldId cvVisitPhase = MetafieldIdFactory.getCompoundMetafieldId(ArgoField.CVD_CV, ArgoField.CV_VISIT_PHASE);

            dq = QueryUtils.createDomainQuery("TrainVisitDetails")
                    .addDqPredicate(PredicateFactory.eq(RailField.RVDTLSD_DIRECTION, TrainDirectionEnum.OUTBOUND));
            //.addDqPredicate(PredicateFactory.in(cvVisitPhase, CVD_VISIT_ACTIVE_PHASES));

        }
        return HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
    }


    private DomainQuery getActiveUnitsAssociatedWithTrain() {
        LOGGER.debug("getActiveUnitsAssociatedWithTrain")
        Object[] activeTransitState = [UfvTransitStateEnum.S40_YARD];
        MetafieldId obCvMode = MetafieldIdFactory.getCompoundMetafieldId(InventoryField.UFV_ACTUAL_OB_CV, ArgoField.CV_CARRIER_MODE);
        /*MetafieldId ibCvMode = MetafieldIdFactory.getCompoundMetafieldId(InventoryField.UFV_ACTUAL_IB_CV, ArgoField.CV_CARRIER_MODE);
        Junction onTrainDisjunction = PredicateFactoryBase.disjunction()
                .add(PredicateFactory.eq(ibCvMode, LocTypeEnum.TRAIN))
                .add(PredicateFactory.eq(obCvMode, LocTypeEnum.TRAIN));*/
        DomainQuery dq = QueryUtils.createDomainQuery("UnitFacilityVisit")
                .addDqPredicate(PredicateFactory.in(InventoryField.UFV_TRANSIT_STATE, activeTransitState))
                .addDqPredicate(PredicateFactory.eq(obCvMode, LocTypeEnum.TRAIN))
        //.addDqPredicate(onTrainDisjunction);
        return dq;
    }

    private def adaptor = ExtensionUtils.getLibrary(ContextHelper.getThreadUserContext(), "TLOAdaptor");

    private static final String TEXT_REQUEST_RAILCARTYPE = "n4tlo:RailcarTypeRequest";
    private static final String TEXT_REQUEST_RAILCAR = "n4tlo:RailcarRequest";
    private static final String TEXT_REQUEST_CONTAINER = "n4tlo:ContainerRequest";
    private static final String TEXT_REQUEST_CONTAINERTRANSIT = "n4tlo:ContainerTransitRequest";
    private static final String TEXT_REQUEST_SUBTRAIN = "n4tlo:SubTrainRequest";
    //private static final String TEXT_REQUEST_PING = "n4tlo:PingRequest";

    private static final String TEXT_RAILCARTYPE = "RailcarType";
    private static final String TEXT_RAILCAR = "Railcar";
    private static final String TEXT_CONTAINER = "Container";
    private static final String TEXT_WI = "WorkInstruction";
    private static final String TEXT_TRAIN_VISIT = "TrainVisitDetails";

    private static final String HANDLER_RAILCAR_TYPE = "RailCarTypeMessageHandler";
    private static final String HANDLER_RAILCAR = "RailCarMessageHandler";
    private static final String HANDLER_CONTAINER = "ContainerMessageHandler";
    private static final String HANDLER_CONTAINERTRANSIT = "ContainerTransitMessageHandler";
    private static final String HANDLER_SUBTRAIN = "SubTrainMessageHandler";

    private static final String TAG_COMMON_HEADER = "<common:header>";

    private static final List<String> ALLOWED_WI_MOVE_STAGE = [WiMoveStageEnum.PLANNED, WiMoveStageEnum.CARRY_UNDERWAY];
    private static final List<String> ALLOWED_WI_MOVE_KIND = [WiMoveKindEnum.RailDisch, WiMoveKindEnum.RailLoad, WiMoveKindEnum.VeslDisch, WiMoveKindEnum.YardMove];
    private static final List<String> CVD_VISIT_ACTIVE_PHASES = [CarrierVisitPhaseEnum.CREATED, CarrierVisitPhaseEnum.INBOUND, CarrierVisitPhaseEnum.ARRIVED, CarrierVisitPhaseEnum.WORKING];

    private static final Logger LOGGER = Logger.getLogger(DPWVTloRequestHandler.class);
}