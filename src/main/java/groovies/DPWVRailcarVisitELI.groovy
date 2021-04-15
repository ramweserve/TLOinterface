import com.navis.argo.ContextHelper
import com.navis.external.framework.entity.AbstractEntityLifecycleInterceptor
import com.navis.external.framework.entity.EEntityView
import com.navis.external.framework.util.EFieldChanges
import com.navis.external.framework.util.EFieldChangesView
import com.navis.external.framework.util.ExtensionUtils
import com.navis.rail.RailField
import com.navis.rail.business.entity.RailcarVisit
import org.apache.log4j.Logger

class DPWVRailcarVisitELI extends AbstractEntityLifecycleInterceptor {

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

    private void updateTlo(RailcarVisit railcarVisit, EFieldChangesView inFieldChanges) {
        try {
            LOGGER.debug("inFieldChanges: "+inFieldChanges);
            //@TODO: Add a IF condition to proceed only if the fieldchanges on below fields
            //ID, damaged, inspect, IB, OB, dest, OB sequence, orientation
            if (inFieldChanges.hasFieldChange(RailField.RCARV_ID)
                    || inFieldChanges.hasFieldChange(RailField.RCARV_INSPECTION_STATUS)
                    || inFieldChanges.hasFieldChange(RailField.RCARV_TRAIN_VISIT_INBOUND)
                    || inFieldChanges.hasFieldChange(RailField.RCARV_TRAIN_VISIT_OUTBOUND)
                    || inFieldChanges.hasFieldChange(RailField.RCARV_DESTINATION)
                    || inFieldChanges.hasFieldChange(RailField.RCARV_OUT_SEQ)
                    /*|| inFieldChanges.hasFieldChange(RailField.POS_ORIENTATION)*/
                    || inFieldChanges.hasFieldChange(RailField.RCARSTT_IS_DAMAGED)) //damage status cannot be captured here, as it is part of RailcarStat
            {
                LOGGER.debug("Insp status: " + railcarVisit.getRcarvInspectionStatus());
                Map<String, String> map = new HashMap<String, String>();
                map.put(adaptor.RAILCAR_VISIT_OBJECT, railcarVisit);
                adaptor.convertN4UpdateToTloNotification(adaptor.TEXT_NOTIFICATION__RAILCAR, map);
            }
        } catch (Exception e) {
            LOGGER.error("Exception in updateTlo: " + e.getMessage() + " : " + e);
        }
    }

    private def adaptor = ExtensionUtils.getLibrary(ContextHelper.getThreadUserContext(), "TLOAdaptor");
    private static final Logger LOGGER = Logger.getLogger(DPWVRailcarVisitELI.class);
}
