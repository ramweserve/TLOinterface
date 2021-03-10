package groovies

import com.navis.argo.ContextHelper
import com.navis.argo.business.api.ArgoUtils
import com.navis.argo.business.api.GroovyApi
import com.navis.argo.business.atoms.IntegrationActionStatusEnum
import com.navis.argo.business.atoms.IntegrationTypeEnum
import com.navis.argo.business.integration.IntegrationError
import com.navis.argo.business.integration.IntegrationServiceMessage
import com.navis.carina.integrationservice.business.IntegrationService
import com.navis.framework.IntegrationServiceField
import com.navis.framework.business.Roastery
import com.navis.framework.business.atoms.IntegrationServiceDirectionEnum
import com.navis.framework.persistence.HibernateApi
import com.navis.framework.portal.QueryUtils
import com.navis.framework.portal.query.DomainQuery
import com.navis.framework.portal.query.PredicateFactory
import com.navis.framework.util.ClusterNodeUtils
import com.navis.framework.util.scope.ScopeCoordinates
import com.navis.services.business.event.Event
import com.syncrotess.external.n4_tlo.notification.containerdelete.ContainerDeleteNotification
import com.syncrotess.external.n4_tlo.common.types.Header
import org.apache.log4j.Level
import org.apache.log4j.Logger

import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBException
import javax.xml.bind.Marshaller
import javax.xml.datatype.DatatypeFactory
import javax.xml.datatype.XMLGregorianCalendar

class TLOAdaptor {

    public String jaxbToXml(Class inClass, Object inObject) {

        try {
            LOGGER.setLevel(Level.DEBUG);
            JAXBContext jaxbContext = JAXBContext.newInstance(inClass);

            //LOGGER.debug("jaxbContext: "+jaxbContext);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            LOGGER.debug("jaxbMarshaller: " + jaxbMarshaller);
            //jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter xmlMessageWriter = new StringWriter();
            LOGGER.debug("writer created");
            jaxbMarshaller.marshal(inObject, xmlMessageWriter);
            LOGGER.debug("conversion completed");
            return xmlMessageWriter.toString();

        } catch (JAXBException e) {
            //throw new TloException("Error creation - Object to Xml : "+e.getMessage());
            LOGGER.error("Error creation - Object to Xml : " + e.getMessage() + " :: " + e);

        } catch (Exception e) {
            //throw new TloException("Error in jaxbToXml method : "+e.getMessage());
            LOGGER.error("Error in jaxbToXml method : " + e.getMessage());
        }
        return null;
    }

    public void recordIntegrationError(Exception inException, String errorMessageBody, String errorDescription, IntegrationTypeEnum integrationTypeEnum, IntegrationActionStatusEnum integrationActionStatusEnum, String eventId, String businessKey) {
        try {
            IntegrationError error = new IntegrationError();
            error.setIerrFacility(ContextHelper.getThreadFacility());
            error.setIerrIntegrationType(integrationTypeEnum);
            error.setIerrIntegrationStatus(integrationActionStatusEnum);
            error.setIerrBusinessKey(businessKey);
            error.setIerrEventId(eventId);
            error.setIerrErrorFirstRecorded(ArgoUtils.timeNow());
            error.setException(inException.toString());
            error.setIerrMessageBody(errorMessageBody);
            error.setIerrDescription(errorDescription);
            error.setIerrNode(ClusterNodeUtils.getDefinedExecutionNode());

            /*AbstractIntegrationErrorHandler integrationRetryHandler = getRetryHandler(error);
            if (integrationRetryHandler != null) {
                integrationRetryHandler.updateError(error, inException);
            }*/
            HibernateApi.getInstance().save(error);
            LOGGER.debug("Integration error recorded");

        } catch (Exception ex) {
            LOGGER.error("Exception in recordIntegrationError : " + ex.getMessage());
        }
    }

    public IntegrationServiceMessage createIntegrationSrcMsg(Event inEvent, String inEntityId, IntegrationService inIntegrationService, String inMessagePayload) {
        IntegrationServiceMessage integrationServiceMessage = new IntegrationServiceMessage();
        try {
            if (inEvent) {
                integrationServiceMessage.setIsmEventPrimaryKey((Long) inEvent.getEvntEventType().getPrimaryKey());
                integrationServiceMessage.setIsmEntityClass(inEvent.getEventAppliedToClass());
                integrationServiceMessage.setIsmEventTypeId(inEvent.getEventTypeId());
            }
            integrationServiceMessage.setIsmEntityNaturalKey(inEntityId);
            if (inIntegrationService) {
                integrationServiceMessage.setIsmIntegrationService(inIntegrationService);
                integrationServiceMessage.setIsmFirstSendTime(ArgoUtils.timeNow());
                integrationServiceMessage.setIsmLastSendTime(ArgoUtils.timeNow());
            }
            integrationServiceMessage.setIsmMessagePayload(inMessagePayload);

            IntegrationServMessageSequenceProvider sequenceProvider = new IntegrationServMessageSequenceProvider();
            integrationServiceMessage.setIsmSeqNbr(new IntegrationServMessageSequenceProvider().getNextSequenceId());
            //integrationServiceMessage.setIsmSeqNbr(IntegrationServMessageSequenceProvider.getNextSequenceId());

            ScopeCoordinates scopeCoordinates = ContextHelper.getThreadUserContext().getScopeCoordinate();
            Long scopeLevel = ScopeCoordinates.GLOBAL_LEVEL;
            String scopeGkey = null;
            if (!scopeCoordinates.isScopeGlobal()) {
                scopeLevel = new Long(ScopeCoordinates.getScopeId(4));
                scopeGkey = (String) scopeCoordinates.getScopeLevelCoord(scopeLevel.intValue());
            }
            integrationServiceMessage.setIsmScopeGkey(scopeGkey);
            integrationServiceMessage.setIsmScopeLevel(scopeLevel);
            HibernateApi.getInstance().save(integrationServiceMessage);

        } catch (Exception e) {
            LOGGER.error("Exception in createIntegrationSrcMsg : " + e.getMessage());
        }
        HibernateApi.getInstance().flush();

        return integrationServiceMessage;
    }

    public void pushOutboundJmsMessage(String inMessage, String queueName) {
        if (ArgoUtils.isNotEmpty(inMessage) && ArgoUtils.isNotEmpty(queueName)) {
            new GroovyApi().sendXml(queueName, inMessage);
            LOGGER.debug("message sent to "+queueName);
            try {
                IntegrationService iServ = getUriFromIntegrationService(queueName, IntegrationServiceDirectionEnum.OUTBOUND);
                String msg = inMessage.length() > DB_CHAR_LIMIT? inMessage.substring(0, DB_CHAR_LIMIT) : inMessage;
                createIntegrationSrcMsg(null, null, iServ, msg);

            } catch (Exception e) {
                LOGGER.error("Exception in pushOutboundJmsMessage : "+e.getMessage());
            }
        }
    }

    public IntegrationService getUriFromIntegrationService(String inName, IntegrationServiceDirectionEnum inDirection) {
        DomainQuery dq = QueryUtils.createDomainQuery("IntegrationService")
                .addDqPredicate(PredicateFactory.eq(IntegrationServiceField.INTSERV_NAME, inName))
                .addDqPredicate(PredicateFactory.eq(IntegrationServiceField.INTSERV_DIRECTION, inDirection))
                .addDqPredicate(PredicateFactory.eq(IntegrationServiceField.INTSERV_ACTIVE, Boolean.TRUE));
        return (IntegrationService) Roastery.getHibernateApi().getUniqueEntityByDomainQuery(dq);
    }

    public Header buildHeader() {
        Header headerType = new Header();
        headerType.setSenderId(HEADER_SENDER_ID);
        headerType.setReceiverId(HEADER_RECEIVER_ID);
        headerType.setInterfaceVersion(HEADER_INTERFACE_ID);
        headerType.setTimestamp(getCurrentTimeStamp());
        headerType.setRequestResponseId(HEADER_REQUEST_RESPONSE_ID);
        return headerType;
    }

    public XMLGregorianCalendar getCurrentTimeStamp() {
        Date date = Calendar.getInstance().getTime();
        //DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss.SSS");
        //return dateFormat.format(date);
        XMLGregorianCalendar xmlDate;
        try {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(date);
            xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
            LOGGER.debug("xmlDate : "+xmlDate);
            return xmlDate;

        } catch (Exception e) {
            LOGGER.error("Exception in getCurrentTimeStamp : "+ e.getMessage());
        }
    }

    public void convertN4UpdateToTloNotification(String inNotificationType, Map inParamMap) {
        try {
            LOGGER.debug("convertN4UpdateToTloNotification BEGIN");

            if(TEXT_NOTIFICATION__CONTAINER_DELETE.equals(inNotificationType)) {
                ContainerDeleteNotification containerDeleteNotification = new ContainerDeleteNotification();
                containerDeleteNotification.setHeader(buildHeader());
                containerDeleteNotification.setContainerId(inParamMap.get(UNIT_ID));

                Class classContainerDeleteNotification = Class.forName("com.syncrotess.external.n4_tlo.notification.containerdelete.ContainerDeleteNotification");
                LOGGER.debug("classContainerDeleteNotification: "+classContainerDeleteNotification);
                pushOutboundJmsMessage(jaxbToXml(classContainerDeleteNotification, containerDeleteNotification), INTEGRATION_SERVICE__N4TOTLO);

            } else if(TEXT_NOTIFICATION__CONTAINER_UPDATE.equals(inNotificationType)) {

            } else if(TEXT_NOTIFICATION__CONTAINER_TRANSIT_DELETE.equals(inNotificationType)) {

            } else if(TEXT_NOTIFICATION__CONTAINER_TRANSIT_UPDATE.equals(inNotificationType)) {

            } else if(TEXT_NOTIFICATION__SUBTRAIN_NOTIFICATION.equals(inNotificationType)) {

            } else if(TEXT_NOTIFICATION__T3_DONE.equals(inNotificationType)) {

            }

        } catch (Exception e) {
            LOGGER.error("Exception in convertN4UpdateToTloNotification : "+e.getMessage());

        }
    }


    public static class IntegrationServMessageSequenceProvider extends com.navis.argo.business.model.ArgoSequenceProvider {
        public Long getNextSequenceId() {
            return super.getNextSeqValue(serviceMsgSequence, (Long)ContextHelper.getThreadFacilityKey());
        }
        private String serviceMsgSequence = "INT_SEQ";
    }

    private void logMsg(Object inMsg) {
        LOGGER.debug(inMsg);
    }

    public static final String TEXT_JMS_DESTINATION = "JMSDestination";
    public static final String INTEGRATION_SERVICE__N4TOTLO = "n4totloqueue";
    public static final String BUSINESS_KEY__TLO_INTERFACE = "tlointerface";

    public static final String TEXT_NOTIFICATION__CONTAINER_DELETE = "ContainerDelete";
    public static final String TEXT_NOTIFICATION__CONTAINER_UPDATE = "ContainerUpdate";
    public static final String TEXT_NOTIFICATION__CONTAINER_TRANSIT_DELETE = "ContainerTransitDelete";
    public static final String TEXT_NOTIFICATION__CONTAINER_TRANSIT_UPDATE = "ContainerTransitUpdate";
    public static final String TEXT_NOTIFICATION__SUBTRAIN_NOTIFICATION = "SubTrain";
    //public static final String TEXT_NOTIFICATION__TRAIN_LOAD_PLAN = "TrainLoadPlan"; //TLO to N4
    public static final String TEXT_NOTIFICATION__T3_DONE = "T3Done";

    public static final String UNIT_ID = "unitId";

    public static final String SCALE_UNIT_CM = "cm";
    public static final String SCALE_UNIT_KG = "kg";
    public static final String TEXT_TRUE = "true";

    private static final int DB_CHAR_LIMIT = 3000;

    private static final String HEADER_SENDER_ID = "N4";
    private static final String HEADER_RECEIVER_ID = "TLO";
    private static final String HEADER_INTERFACE_ID = "1.0";
    private static final String HEADER_REQUEST_RESPONSE_ID = "requestResponseId";

    private Logger LOGGER = Logger.getLogger(TLOAdaptor.class);
}