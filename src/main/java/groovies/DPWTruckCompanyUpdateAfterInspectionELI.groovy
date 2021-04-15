/*
 * Copyright (c) 2016 WeServe LLC. All Rights Reserved.
 *
 */


import com.navis.argo.ContextHelper
import com.navis.argo.business.atoms.DrayStatusEnum
import com.navis.argo.business.atoms.UnitCategoryEnum
import com.navis.external.framework.entity.AbstractEntityLifecycleInterceptor
import com.navis.external.framework.entity.EEntityView
import com.navis.external.framework.util.EFieldChanges
import com.navis.external.framework.util.EFieldChangesView
import com.navis.external.framework.util.ExtensionUtils
import com.navis.framework.business.Roastery
import com.navis.framework.persistence.Entity
import com.navis.inventory.InvField
import com.navis.inventory.InventoryField
import com.navis.inventory.business.api.UnitFinder
import com.navis.inventory.business.units.Routing
import com.navis.inventory.business.units.Unit
import com.navis.inventory.business.units.UnitFacilityVisit
import org.apache.log4j.Level
import org.apache.log4j.Logger

/**When one of the containers is designated for an off-dock inspection,  Bonded Trucking Company is assigned to move the container to/from the Customs facility.
 *  Upon the return of the container the 'Trucking Company' field is overwritten with the name of the (bonded trucking company)trucking company that has returned the container to the terminal.
 *  This groovy is written to prevent the 'Trucking Company' field from being overwritten
 * This groovy will check for the bonded trucking company and trucking company values.
 * if they are same and if it has field change value  with old value of trucking company different
 * from bonded trucking company value and  the category is import and the status is Dray out and back then it will update the trucking company field with the old value.
 *
 */
class DPWTruckCompanyUpdateAfterInspectionELI extends AbstractEntityLifecycleInterceptor {
    public void validateChanges(EEntityView inEntity, EFieldChangesView inFieldChanges) {
        LOGGER.setLevel(Level.DEBUG);
        LOGGER.debug(inEntity.getEntityName());
        LOGGER.debug("Field Changes :: "+inFieldChanges);
        super.validateChanges(inEntity, inFieldChanges);
    }

    public void onCreate(EEntityView eentityview, EFieldChangesView efieldchangesview, EFieldChanges efieldchanges) {
        updateTlo(eentityview._entity, efieldchangesview); //ContainerUpdateNotification
    }

    public void onUpdate(EEntityView eentityview, EFieldChangesView inOriginalFieldChanges, EFieldChanges efieldchanges) {
        LOGGER.setLevel(Level.DEBUG);
        LOGGER.debug(" Original Field Changes :: "+inOriginalFieldChanges);
        LOGGER.debug(" More Field Changes :: "+efieldchanges);
        Unit unit=eentityview._entity;
        if (unit == null) {
            log("unit is null, ignoring the call");
            return;
        }
        if (inOriginalFieldChanges == null) {
            log(" DPWTruckCompanyUpdateAfterInspectionELIGroovy called with null field changes, ignoring the call");
            return;
        }
        UnitCategoryEnum unitcategory=unit.getUnitCategory();
        if (unitcategory!=null && unitcategory.equals(UnitCategoryEnum.IMPORT)) {
            DrayStatusEnum drayStatus = unit.getUnitDrayStatus();
            if ((drayStatus != null) && (drayStatus.equals(DrayStatusEnum.OFFSITE)||drayStatus.equals(DrayStatusEnum.DRAYIN))) {
                if (inOriginalFieldChanges.hasFieldChange(InventoryField.UNIT_ROUTING)) {
                    Routing newrouting=(Routing) inOriginalFieldChanges.findFieldChange(InventoryField.UNIT_ROUTING).getNewValue();
                    Routing oldrouting= (Routing) inOriginalFieldChanges.findFieldChange(InventoryField.UNIT_ROUTING).getPriorValue();
                    if (newrouting!=null && oldrouting !=null)
                    {  String trkc=newrouting.getRtgTruckingCompany()!=null ?newrouting.getRtgTruckingCompany().getBzuId():null;
                        String oldtrkc=oldrouting.getRtgTruckingCompany() != null ? oldrouting.getRtgTruckingCompany().getBzuId():null;
                        if (trkc != null && oldtrkc !=null) {
                            String routingbondtrkcval=newrouting.getRtgBondTruckingCompany()!=null?newrouting.getRtgBondTruckingCompany().getBzuId():null;
                            if(routingbondtrkcval!=null)
                            {
                                if (trkc.equals(routingbondtrkcval) && !(trkc.equals(oldtrkc)))
                                {
                                    efieldchanges.setFieldChange(InventoryField.UNIT_ROUTING, oldrouting);
                                }
                            }
                        }
                    }
                }
            }
        }
        super.onUpdate(eentityview, inOriginalFieldChanges, efieldchanges);

        updateTlo(eentityview._entity, inOriginalFieldChanges); //ContainerUpdateNotification
    }

    @Override
    void preDelete(Entity inEntity) {
        super.preDelete(inEntity);

        //@TODO: If error occurs while deleting the unit, the below logic would get executed, which should not be happened.
        // Moving this logic validateDelete() which ends in same result. Do we have any api to check the deletion eligibility?
        updateTlo(inEntity, null); //ContainerUpdateNotification
    }


    //UFV ELI - Position update,
    //Unit ELI - id, routing,
    //Equipment - lengthType, tareWt, height, isoCode,

    //check if unit is associated with OB train - is Rail container wrt OB?
    private void updateTlo(Unit inUnit, EFieldChangesView inFieldChanges) {
        LOGGER.debug("updateTlo: "+inFieldChanges);

        UnitFacilityVisit ufv = inUnit.getUnitActiveUfvNowActive();
        Map<String, String> unitMap = new HashMap<String, String>();
        unitMap.put(adaptor.UFV_OBJECT, ufv);
        if (inFieldChanges == null) { //unit Delete call
            adaptor.convertN4UpdateToTloNotification(adaptor.TEXT_NOTIFICATION__CONTAINER_DELETE, unitMap);

        } else if (ufv && adaptor && adaptor.isRailContainer(ufv)) {


                    || inFieldChanges.hasFieldChange(InvField.UNIT_ROUTING)
                    || inFieldChanges.hasFieldChange(InvField.UNIT_IS_OOG)



            adaptor.convertN4UpdateToTloNotification(adaptor.TEXT_NOTIFICATION__CONTAINER_UPDATE, unitMap);
        }
    }

    private def adaptor = ExtensionUtils.getLibrary(ContextHelper.getThreadUserContext(), "TLOAdaptor");
    private Logger LOGGER = Logger.getLogger(DPWTruckCompanyUpdateAfterInspectionELI.class);
}
