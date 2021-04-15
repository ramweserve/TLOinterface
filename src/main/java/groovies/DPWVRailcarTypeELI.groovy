import com.navis.argo.ContextHelper
import com.navis.external.framework.entity.AbstractEntityLifecycleInterceptor
import com.navis.external.framework.entity.EEntityView
import com.navis.external.framework.util.EFieldChanges
import com.navis.external.framework.util.EFieldChangesView
import com.navis.external.framework.util.ExtensionUtils
import com.navis.rail.business.api.RailCompoundField
import com.navis.rail.business.entity.RailcarDetails
import com.navis.rail.business.entity.RailcarType
import org.apache.log4j.Level
import org.apache.log4j.Logger

class DPWVRailcarTypeELI extends AbstractEntityLifecycleInterceptor {

    @Override
    void onCreate(EEntityView inEntity, EFieldChangesView inOriginalFieldChanges, EFieldChanges inMoreFieldChanges) {
        super.onCreate(inEntity, inOriginalFieldChanges, inMoreFieldChanges)
        updateTlo(inEntity._entity, inOriginalFieldChanges);
    }

    @Override
    void onUpdate(EEntityView inEntity, EFieldChangesView inOriginalFieldChanges, EFieldChanges inMoreFieldChanges) {
        super.onUpdate(inEntity, inOriginalFieldChanges, inMoreFieldChanges)
        updateTlo(inEntity._entity, inOriginalFieldChanges);
    }

    private void updateTlo(RailcarType railcarType, EFieldChangesView inFieldChanges) {
        try {
            LOGGER.setLevel(Level.DEBUG);
            RailcarDetails priorRcarDetails = inFieldChanges.findFieldChange(RailCompoundField.RCARTYP_RAILCAR_DETAILS).getPriorValue();
            RailcarDetails newRcarDetails = railcarType.getRcartypRailcarDetails();
            LOGGER.debug("old safe wt: "+priorRcarDetails.getRcdSafeWeightKg() + " Vs new safe-wt: "+railcarType.getRcartypRailcarDetails().getRcdSafeWeightKg());

            if(priorRcarDetails.getRcdSafeWeightKg() != newRcarDetails.getRcdSafeWeightKg()
              || priorRcarDetails.getRcdTareWeightKg() != newRcarDetails.getRcdTareWeightKg()
              || priorRcarDetails.getRcdLengthCm() != newRcarDetails.getRcdLengthCm() ) {

                LOGGER.debug("it has changes");
                Map<String, String> map = new HashMap<String, String>();
                map.put(adaptor.RAILCARTYPE_OBJECT, railcarType);
                adaptor.convertN4UpdateToTloNotification(adaptor.TEXT_NOTIFICATION__RAILCARTYPE, map);
            }

        } catch (Exception e) {
            LOGGER.error("Exception in updateTlo: " + e.getMessage() + " : " + e);
        }
    }

    private def adaptor = ExtensionUtils.getLibrary(ContextHelper.getThreadUserContext(), "TLOAdaptor");
    private static final Logger LOGGER = Logger.getLogger(DPWVRailcarTypeELI.class);
}