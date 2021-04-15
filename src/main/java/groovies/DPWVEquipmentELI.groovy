import com.navis.argo.ContextHelper
import com.navis.argo.business.reference.Equipment
import com.navis.external.framework.entity.AbstractEntityLifecycleInterceptor
import com.navis.external.framework.entity.EEntityView
import com.navis.external.framework.util.EFieldChanges
import com.navis.external.framework.util.EFieldChangesView
import com.navis.external.framework.util.ExtensionUtils
import org.apache.log4j.Level
import org.apache.log4j.Logger

class DPWVEquipmentELI extends AbstractEntityLifecycleInterceptor {

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

    //@TODO: add required field checks and notify
    private void updateTlo(Equipment equipment, EFieldChangesView inFieldChanges) {
        try {
            LOGGER.setLevel(Level.DEBUG);
            LOGGER.debug("inFieldChanges: "+inFieldChanges);

        } catch (Exception e) {
            LOGGER.error("Exception in updateTlo: " + e.getMessage() + " : " + e);
        }
    }

    private def adaptor = ExtensionUtils.getLibrary(ContextHelper.getThreadUserContext(), "TLOAdaptor");
    private static final Logger LOGGER = Logger.getLogger(DPWVEquipmentELI.class);

}