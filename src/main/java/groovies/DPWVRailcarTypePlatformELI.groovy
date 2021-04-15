import com.navis.argo.ContextHelper
import com.navis.external.framework.entity.AbstractEntityLifecycleInterceptor
import com.navis.external.framework.entity.EEntityView
import com.navis.external.framework.util.EFieldChanges
import com.navis.external.framework.util.EFieldChangesView
import com.navis.external.framework.util.ExtensionUtils
import com.navis.rail.RailField
import com.navis.rail.business.api.RailCompoundField
import com.navis.rail.business.entity.RailcarPlatformDetails
import com.navis.rail.business.entity.RailcarTypePlatform
import org.apache.log4j.Level
import org.apache.log4j.Logger

class DPWVRailcarTypePlatformELI extends AbstractEntityLifecycleInterceptor {

    @Override
    void onCreate(EEntityView inEntity, EFieldChangesView inOriginalFieldChanges, EFieldChanges inMoreFieldChanges) {
        super.onCreate(inEntity, inOriginalFieldChanges, inMoreFieldChanges)
        updateTlo(inEntity._entity, inOriginalFieldChanges)
    }

    @Override
    void onUpdate(EEntityView inEntity, EFieldChangesView inOriginalFieldChanges, EFieldChanges inMoreFieldChanges) {
        super.onUpdate(inEntity, inOriginalFieldChanges, inMoreFieldChanges);
        LOGGER.debug("entity: " + inEntity._entity);
        updateTlo(inEntity._entity, inOriginalFieldChanges)
    }

    private void updateTlo(RailcarTypePlatform railcarTypePlatform, EFieldChangesView inFieldChanges) {
        try {
            LOGGER.setLevel(Level.DEBUG);

            LOGGER.debug("inFieldChanges: " + inFieldChanges);
            //@TODO: Add a IF condition to proceed only if the fieldchanges on below fields
            //numberOfSlots, well, deckHeight, maxNetWeight, tareWeight and max20ftContainerWeight

            if (inFieldChanges.hasFieldChange(RailField.RCARTYPLF_RAILCAR_PLATFORM_DETAILS) && railcarTypePlatform.getRcartyplfRailcarType()) {
                RailcarPlatformDetails priorPlatformDetails = inFieldChanges.findFieldChange(RailCompoundField.RCARTYPLF_RAILCAR_PLATFORM_DETAILS).getPriorValue();
                RailcarPlatformDetails newPlatformDetails = railcarTypePlatform.getRcartyplfRailcarPlatformDetails();

                if (priorPlatformDetails.getPlfdFloorHeightCm() != newPlatformDetails.getPlfdFloorHeightCm()
                        || priorPlatformDetails.getPlfdTareWeightKg() != newPlatformDetails.getPlfdTareWeightKg()
                        || priorPlatformDetails.getPlfdWeightMax20InKg() != newPlatformDetails.getPlfdWeightMax20InKg()) {

                    LOGGER.debug("matches");
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(adaptor.RAILCARTYPE_OBJECT, railcarTypePlatform.getRcartyplfRailcarType());
                    adaptor.convertN4UpdateToTloNotification(adaptor.TEXT_NOTIFICATION__RAILCARTYPE, map);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Exception in updateTlo: " + e.getMessage() + " : " + e);
        }
    }

    private def adaptor = ExtensionUtils.getLibrary(ContextHelper.getThreadUserContext(), "TLOAdaptor");
    private static final Logger LOGGER = Logger.getLogger(DPWVRailcarTypePlatformELI.class);
}