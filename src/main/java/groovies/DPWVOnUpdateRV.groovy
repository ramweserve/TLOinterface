import com.navis.argo.ContextHelper
import com.navis.external.framework.util.ExtensionUtils
import com.navis.external.services.AbstractGeneralNoticeCodeExtension
import com.navis.services.business.event.EventFieldChange
import com.navis.services.business.event.GroovyEvent
import org.apache.log4j.Level
import org.apache.log4j.Logger

class DPWVOnUpdateRV extends AbstractGeneralNoticeCodeExtension {

    @Override
        void execute(GroovyEvent inEvent) {
        LOGGER.setLevel(Level.INFO);
        LOGGER.debug("execute - BEGIN");

        Set set = inEvent.getEvent().getEvntFieldChanges();
        LOGGER.debug("field change size : "+set.size());
        Iterator iter = set.iterator();
        EventFieldChange eventFieldChange;
        boolean isEligibleForUpdate = false;
        while (iter.hasNext()) {
            eventFieldChange = (EventFieldChange) iter.next();
            if(CVD_ETD == eventFieldChange.getMetafieldId() || CV_VISIT_PHASE == eventFieldChange.getMetafieldId()) {
                isEligibleForUpdate = true;
                break;
            }
        }

        if(isEligibleForUpdate) {
            def adaptor = ExtensionUtils.getLibrary(ContextHelper.getThreadUserContext(), "TLOAdaptor");
            Map<String, String> map = new HashMap<String, String>();
            map.put(adaptor.TRAIN_VISIT_DETAILS_OBJECT, inEvent.getEntity());
            adaptor.convertN4UpdateToTloNotification(adaptor.TEXT_NOTIFICATION__SUBTRAIN_NOTIFICATION, map);
        }
    }

    private static final String CVD_ETD = "cvdETD";
    private static final String CV_VISIT_PHASE = "cvVisitPhase";
    private static final Logger LOGGER = Logger.getLogger(DPWVOnUpdateRV.class);
}
