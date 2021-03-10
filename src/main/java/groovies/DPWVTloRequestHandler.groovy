package groovies

import com.navis.argo.ContextHelper
import com.navis.argo.business.api.ArgoUtils
import com.navis.argo.business.atoms.IntegrationActionStatusEnum
import com.navis.argo.business.atoms.IntegrationTypeEnum
import com.navis.carina.integrationservice.business.IntegrationService
import com.navis.external.argo.AbstractJmsCodeExtension
import com.navis.external.framework.util.ExtensionUtils
import com.navis.framework.business.atoms.IntegrationServiceDirectionEnum
import com.navis.framework.persistence.HibernateApi
import com.navis.framework.portal.QueryUtils
import com.navis.framework.portal.query.DomainQuery
import com.navis.rail.business.entity.RailcarType
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

            if(adaptor) {
                try {
                    String destination = inProperties.get(adaptor.TEXT_JMS_DESTINATION);
                    if (destination) {
                        String intServName = destination.substring(destination.indexOf("://") + 3);
                        IntegrationService iServ = adaptor.getUriFromIntegrationService(intServName, IntegrationServiceDirectionEnum.INDOUND);
                        adaptor.createIntegrationSrcMsg(null, null, iServ, inXmlMessage);
                    }
                } catch(Exception msgException) {
                    LOGGER.error("Error while recording inbound message : "+ msgException.getMessage());
                }
            }

            if (inXmlMessage.contains(TEXT_REQUEST_RAILCARTYPE)) {
                LOGGER.debug("create RailCarTypeMessageProcessor");
                List<RailcarType> RailcarTypeList = getAllReferenceDataForEntity(TEXT_RAILCARTYPE);
                responseXmlMessage = processRequest("RailCarTypeMessageHandler", RailcarTypeList);

            } else if (inXmlMessage.contains(TEXT_REQUEST_RAILCAR)) {

            } else if (inXmlMessage.contains(TEXT_REQUEST_CONTAINER)) {

            } else if (inXmlMessage.contains(TEXT_REQUEST_CONTAINERTRANSIT)) {

            } else if (inXmlMessage.contains(TEXT_REQUEST_SUBTRAIN)) {

            } else if (inXmlMessage.contains(TEXT_REQUEST_PING)) {

            } else {
                responseXmlMessage = "From N4: No matching request";
            }

            LOGGER.debug("responseXmlMessage: " + responseXmlMessage);

            if(adaptor)
                adaptor.pushOutboundJmsMessage(responseXmlMessage, adaptor.INTEGRATION_SERVICE__N4TOTLO);

            //For test
            //throw new NullPointerException("custom error");

        } catch (Exception e) {
            LOGGER.error("Exception in handleRequest : " + e.getMessage());
            adaptor.recordIntegrationError(e, null, null, IntegrationTypeEnum.JMS_OUTBOUND_TEXT, IntegrationActionStatusEnum.ERROR, null, adaptor.BUSINESS_KEY__TLO_INTERFACE);
        }
    }

    private String processRequest(String handlerName, List entityList) {
        def messageHandler = ExtensionUtils.getLibrary(ContextHelper.getThreadUserContext(), handlerName);
        LOGGER.debug(handlerName + " - messageHandler: "+messageHandler);
        return messageHandler.processRequest(entityList, adaptor.buildHeader(), adaptor);
    }

    private List getAllReferenceDataForEntity(String entityName) {
        DomainQuery dq = QueryUtils.createDomainQuery(entityName);
        return HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
    }


    private def adaptor = ExtensionUtils.getLibrary(ContextHelper.getThreadUserContext(), "TLOAdaptor");

    private static final String TEXT_REQUEST_RAILCARTYPE = "n4tlo:RailcarTypeRequest";
    private static final String TEXT_REQUEST_RAILCAR = "n4tlo:RailcarRequest";
    private static final String TEXT_REQUEST_CONTAINER = "n4tlo:ContainerRequest";
    private static final String TEXT_REQUEST_CONTAINERTRANSIT = "n4tlo:ContainerTransitRequest";
    private static final String TEXT_REQUEST_SUBTRAIN = "n4tlo:SubTrainRequest";
    private static final String TEXT_REQUEST_PING = "n4tlo:PingRequest";

    private static final String TEXT_RAILCARTYPE = "RailcarType";

    private static final Logger LOGGER = Logger.getLogger(DPWVTloRequestHandler.class);
}