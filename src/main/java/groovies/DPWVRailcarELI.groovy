import com.navis.argo.ContextHelper
import com.navis.external.framework.entity.AbstractEntityLifecycleInterceptor
import com.navis.external.framework.entity.EEntityView
import com.navis.external.framework.util.EFieldChanges
import com.navis.external.framework.util.EFieldChangesView
import com.navis.external.framework.util.ExtensionUtils
import com.navis.rail.RailField
import com.navis.rail.business.api.RailCompoundField
import com.navis.rail.business.entity.Railcar
import com.navis.rail.business.entity.RailcarDetails
import com.navis.rail.business.entity.RailcarType
import com.navis.rail.business.entity.RailcarVisit
import com.navis.rail.business.entity.Railroad
import org.apache.log4j.Level
import org.apache.log4j.Logger

class DPWVRailcarELI extends AbstractEntityLifecycleInterceptor {

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

    private void updateTlo(Railcar railcar, EFieldChangesView inFieldChanges) {
        try {
            LOGGER.setLevel(Level.DEBUG);
            LOGGER.debug("inFieldChanges: "+inFieldChanges);
            LOGGER.debug("railcar: "+railcar);

            LOGGER.debug("template type: "+inFieldChanges.findFieldChange(RailField.RCAR_RAILCAR_TEMPLATE_TYPE));
            LOGGER.debug("tier count: "+inFieldChanges.findFieldChange(RailField.RCD_MAX_TIERS_PER_PLATFORM));
            LOGGER.debug("owner: "+inFieldChanges.findFieldChange(RailField.RCAR_OWNER));

            RailcarType priorRcarType = inFieldChanges.hasFieldChange(RailField.RCAR_RAILCAR_TEMPLATE_TYPE)? inFieldChanges.findFieldChange(RailField.RCAR_RAILCAR_TEMPLATE_TYPE).getPriorValue() : null;
            //Long priorRcarTierCount = inFieldChanges.hasFieldChange(RailField.RCD_MAX_TIERS_PER_PLATFORM)? inFieldChanges.findFieldChange(RailField.RCD_MAX_TIERS_PER_PLATFORM).getPriorValue() : null;
            Railroad priorRcarOwner = inFieldChanges.hasFieldChange(RailField.RCAR_OWNER)? inFieldChanges.findFieldChange(RailField.RCAR_OWNER).getPriorValue() : null;
            RailcarDetails priorRcarDetails = inFieldChanges.hasFieldChange(RailField.RCARTYP_RAILCAR_DETAILS)? inFieldChanges.findFieldChange(RailCompoundField.RCARTYP_RAILCAR_DETAILS).getPriorValue() : null;
            RailcarDetails newRcarDetails = railcar.getRcarRailcarDetails();

            //@TODO: Add a IF condition to proceed only if the fieldchanges on below fields
            //owner, max-wt, length and reeferSuitableTopTier, reeferSuitableBottomTier
            if( (priorRcarType != null && priorRcarType.getRcartypId() != railcar.getRcarRailcarTemplateType().getRcartypId())
                    /*|| (priorRcarTierCount != null && priorRcarTierCount != newRcarDetails.getRcdMaxTiersPerPlatform())*/
                    || (priorRcarOwner != null && priorRcarOwner.getBzuId() != railcar.getRcarOwner().getBzuId())
                    || (priorRcarDetails != null && (priorRcarDetails.getRcdSafeWeightKg() != newRcarDetails.getRcdSafeWeightKg()
                        || priorRcarDetails.getRcdFlatCarType() != newRcarDetails.getRcdFlatCarType()
                        || priorRcarDetails.getRcdLengthCm() != newRcarDetails.getRcdLengthCm())) ) {

                RailcarVisit railcarVisit = railcar.findRailcarVisit(railcar.getRcarId());
                if (railcarVisit == null) {
                    LOGGER.debug("Railcar Visit not available for "+railcar.getRcarId());
                    return;
                }
                Map<String, String> map = new HashMap<String, String>();
                map.put(adaptor.RAILCAR_VISIT_OBJECT, railcarVisit);
                adaptor.convertN4UpdateToTloNotification(adaptor.TEXT_NOTIFICATION__RAILCAR, map);
            }

        } catch (Exception e) {
            LOGGER.error("Exception in updateTlo: " + e.getMessage() + " : " + e);
        }
    }

    private def adaptor = ExtensionUtils.getLibrary(ContextHelper.getThreadUserContext(), "TLOAdaptor");
    private static final Logger LOGGER = Logger.getLogger(DPWVRailcarELI.class);

}
