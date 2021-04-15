import com.navis.argo.ContextHelper
import com.navis.carina.integrationservice.business.IntegrationService
import com.navis.external.argo.AbstractJmsCodeExtension
import com.navis.external.framework.util.ExtensionUtils
import com.navis.framework.business.atoms.IntegrationServiceDirectionEnum
import com.syncrotess.external.n4_tlo.PingResponse
import com.syncrotess.external.n4_tlo.common.types.RequestMessage
import org.apache.log4j.Level
import org.apache.log4j.Logger

class DPWTloPingPongHandler extends AbstractJmsCodeExtension {

    void execute(Object inPayload, Map<String, Object> inProperties) {
        LOGGER.setLevel(Level.DEBUG);
        if (adaptor) {
            try {
                String destination = inProperties.get(adaptor.TEXT_JMS_DESTINATION);
                if (destination) {
                    String intServName = destination.substring(destination.indexOf("://") + 3);
                    IntegrationService iServ = adaptor.getUriFromIntegrationService(intServName, IntegrationServiceDirectionEnum.INDOUND);
                    adaptor.createIntegrationSrcMsg(null, null, iServ, inPayload.toString());
                }

                LOGGER.debug("inPayload: "+inPayload);
                String responseXmlMessage;
                String payloadStr = inPayload? inPayload.toString() : null;
                if(payloadStr.contains(TEXT_REQUEST_PING)) {
                    RequestMessage requestMessage = (RequestMessage) adaptor.xmlToJaxb(Class.forName(adaptor.REQUEST_MESSAGE_CLASS), payloadStr);
                    LOGGER.debug("requestMessage : " + requestMessage);
                    if (requestMessage != null) {
                        String requestResponseId = requestMessage.getHeader() ? requestMessage.getHeader().getRequestResponseId() : "-1";
                        LOGGER.debug("--PING-- " + requestResponseId);
                        PingResponse pingResponse = new PingResponse();
                        pingResponse.setHeader(adaptor.buildHeader(requestResponseId));
                        responseXmlMessage = adaptor.jaxbToXml(Class.forName(adaptor.PING_RESPONSE_CLASS), pingResponse);
                    }
                } else {
                    responseXmlMessage = "From N4: No matching request";
                }
                adaptor.pushOutboundJmsMessage(responseXmlMessage, adaptor.INTEGRATION_SERVICE__PONG);

            } catch (Exception msgException) {
                LOGGER.error("Error in DPWTloPingPongHandler : " + msgException.getMessage());
            }
        }
    }

    private static final String TEXT_REQUEST_PING = "n4tlo:PingRequest";
    private def adaptor = ExtensionUtils.getLibrary(ContextHelper.getThreadUserContext(), "TLOAdaptor");
    private static final Logger LOGGER = Logger.getLogger(DPWTloPingPongHandler.class);
}