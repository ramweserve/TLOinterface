import com.navis.argo.ContextHelper
import com.navis.argo.business.atoms.WiMoveKindEnum
import com.navis.argo.business.atoms.WiMoveStageEnum
import com.navis.external.framework.entity.AbstractEntityLifecycleInterceptor
import com.navis.external.framework.entity.EEntityView
import com.navis.external.framework.util.EFieldChanges
import com.navis.external.framework.util.EFieldChangesView
import com.navis.external.framework.util.ExtensionUtils
import com.navis.framework.persistence.Entity
import com.navis.inventory.MovesField
import com.navis.inventory.business.moves.WorkInstruction
import com.navis.inventory.business.units.UnitFacilityVisit
import org.apache.log4j.Logger

class DPWVWorkInstructionELI extends AbstractEntityLifecycleInterceptor {

    @Override
    void onCreate(EEntityView inEntity, EFieldChangesView inOriginalFieldChanges, EFieldChanges inMoreFieldChanges) {
        super.onCreate(inEntity, inOriginalFieldChanges, inMoreFieldChanges)
        updateTlo(inEntity._entity, false, inOriginalFieldChanges); //ContainerTransitUpdateNotification
    }

    @Override
    void onUpdate(EEntityView inEntity, EFieldChangesView inOriginalFieldChanges, EFieldChanges inMoreFieldChanges) {
        super.onUpdate(inEntity, inOriginalFieldChanges, inMoreFieldChanges)
        updateTlo(inEntity._entity, false, inOriginalFieldChanges); //ContainerTransitUpdateNotification
    }

    @Override
    void preDelete(Entity inEntity) {
        super.preDelete(inEntity)
        updateTlo(inEntity, true, null); //ContainerTransitDeleteNotification
    }

    private void updateTlo(WorkInstruction inWorkInstruction, boolean isDelete, EFieldChangesView inFieldChanges) {
        try {
            UnitFacilityVisit ufv = inWorkInstruction.getWiUfv();
            if (ufv && adaptor && adaptor.isRailContainer(ufv)) {
                WiMoveStageEnum moveStageEnum = inWorkInstruction.getWiMoveStage();
                if (ALLOWED_MOVE_KIND.contains(inWorkInstruction.getWiMoveKind()) && ALLOWED_WI_MOVE_STAGE.contains(moveStageEnum)) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(adaptor.WI_OBJECT, inWorkInstruction);
                    if (isDelete) {
                        adaptor.convertN4UpdateToTloNotification(adaptor.TEXT_NOTIFICATION__CONTAINER_TRANSIT_DELETE, map);

                    } else if (inFieldChanges.hasFieldChange(MovesField.WI_POSITION) || inFieldChanges.hasFieldChange(MovesField.WI_MOVE_STAGE)) {
                        /*map.put(adaptor.CONTAINER_ID, ufv.getPrimaryEqId());
                        map.put(adaptor.FROM_LOCATION, ufv.getUfvLastKnownPosition().getPosLocId());
                        map.put(adaptor.TO_LOCATION, inWorkInstruction.getWiPosition().getPosLocId());
                        map.put(adaptor.WI_STAGE, moveStageEnum);*/
                        adaptor.convertN4UpdateToTloNotification(adaptor.TEXT_NOTIFICATION__CONTAINER_TRANSIT_UPDATE, map);
                    }
                }
            }

        } catch (Exception e) {
            LOGGER.error("Exception in updateTlo: " + e.getMessage() + " : " + e);
        }
    }

    private def adaptor = ExtensionUtils.getLibrary(ContextHelper.getThreadUserContext(), "TLOAdaptor");
    private static final List<WiMoveStageEnum> ALLOWED_WI_MOVE_STAGE = [WiMoveStageEnum.PLANNED, WiMoveStageEnum.CARRY_UNDERWAY, WiMoveStageEnum.COMPLETE];
    private static final List ALLOWED_MOVE_KIND = [WiMoveKindEnum.RailLoad, WiMoveKindEnum.RailDisch, WiMoveKindEnum.VeslDisch];
    private static final Logger LOGGER = Logger.getLogger(DPWVWorkInstructionELI.class);
}