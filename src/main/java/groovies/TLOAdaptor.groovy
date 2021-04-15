import com.navis.argo.ContextHelper
import com.navis.argo.business.api.ArgoUtils
import com.navis.argo.business.api.GroovyApi
import com.navis.argo.business.atoms.*
import com.navis.argo.business.integration.IntegrationError
import com.navis.argo.business.integration.IntegrationServiceMessage
import com.navis.argo.business.model.CarrierVisit
import com.navis.argo.business.reference.EquipType
import com.navis.argo.business.reference.Equipment
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
import com.navis.inventory.business.atoms.UfvTransitStateEnum
import com.navis.inventory.business.moves.WorkInstruction
import com.navis.inventory.business.units.Unit
import com.navis.inventory.business.units.UnitFacilityVisit
import com.navis.rail.business.entity.RailcarDetails
import com.navis.rail.business.entity.RailcarPlatform
import com.navis.rail.business.entity.RailcarPlatformDetails
import com.navis.rail.business.entity.RailcarTypePlatform
import com.navis.rail.business.entity.RailcarVisit
import com.navis.rail.business.entity.TrainVisitDetails
import com.navis.services.business.event.Event
import com.syncrotess.external.n4_tlo.ContainerDeleteNotification
import com.syncrotess.external.n4_tlo.ContainerTransitDeleteNotification
import com.syncrotess.external.n4_tlo.ContainerTransitUpdateNotification
import com.syncrotess.external.n4_tlo.ContainerUpdateNotification
import com.syncrotess.external.n4_tlo.RailcarConsistNotification
import com.syncrotess.external.n4_tlo.RailcarNotification
import com.syncrotess.external.n4_tlo.RailcarTypeNotification
import com.syncrotess.external.n4_tlo.SubTrainNotification
import com.syncrotess.external.n4_tlo.common.types.*
import com.syncrotess.external.n4_tlo.types.ConingStatus
import com.syncrotess.external.n4_tlo.types.Container
import com.syncrotess.external.n4_tlo.types.ContainerLengthTypeWithPositions
import com.syncrotess.external.n4_tlo.types.ContainerLengthTypesWithPositions
import com.syncrotess.external.n4_tlo.types.ContainerTransit
import com.syncrotess.external.n4_tlo.types.ContainerTransitStatus
import com.syncrotess.external.n4_tlo.types.Platform
import com.syncrotess.external.n4_tlo.types.PlatformSlotPosition
import com.syncrotess.external.n4_tlo.types.PlatformType
import com.syncrotess.external.n4_tlo.types.PlatformTypes
import com.syncrotess.external.n4_tlo.types.Platforms
import com.syncrotess.external.n4_tlo.types.Railcar
import com.syncrotess.external.n4_tlo.types.RailcarConsists
import com.syncrotess.external.n4_tlo.types.RailcarDamaged
import com.syncrotess.external.n4_tlo.types.RailcarType
import com.syncrotess.external.n4_tlo.types.Railcars
import com.syncrotess.external.n4_tlo.types.SpecialStowType
import com.syncrotess.external.n4_tlo.types.SubTrain
import com.syncrotess.external.n4_tlo.types.SubTrainStatus
import com.syncrotess.external.n4_tlo.types.TiersLock
import com.syncrotess.external.n4_tlo.types.TrackPosition
import jaxb.processor.DefaultNamespacePrefixMapper
import org.apache.log4j.Level
import org.apache.log4j.Logger

import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBException
import javax.xml.bind.Marshaller
import javax.xml.bind.Unmarshaller
import javax.xml.datatype.DatatypeFactory
import javax.xml.datatype.XMLGregorianCalendar

class TLOAdaptor {

    public String jaxbToXml(Class inClass, Object inObject) {
        try {
            LOGGER.setLevel(Level.DEBUG);
            JAXBContext jaxbContext = JAXBContext.newInstance(inClass);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            LOGGER.debug("jaxb To Xml: " + jaxbMarshaller);
            jaxbMarshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new DefaultNamespacePrefixMapper());
            //jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter xmlMessageWriter = new StringWriter();
            jaxbMarshaller.marshal(inObject, xmlMessageWriter);
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

    public Object xmlToJaxb(Class inClass, String inXmlStr) {
        try {
            LOGGER.setLevel(Level.DEBUG);
            JAXBContext jaxbContext = JAXBContext.newInstance(inClass);
            Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();
            LOGGER.debug("xml To Jaxb: " + jaxbUnMarshaller);
            //jaxbUnMarshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new DefaultNamespacePrefixMapper());

            return jaxbUnMarshaller.unmarshal(new StringReader(inXmlStr));

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

    //@TODO: On receiving same 'inRequestResponseId' in request, can N4 ignore the request? if yes, how the response would be framed?
    public Header buildHeader(String inRequestResponseId) {
        Header header = new Header();
        header.setSenderId(HEADER_SENDER_ID);
        header.setReceiverId(HEADER_RECEIVER_ID);
        header.setInterfaceVersion(HEADER_INTERFACE_ID);
        header.setTimestamp(getXMLGregorianTime(new Date()));
        if(inRequestResponseId == null) { //notification
            header.setRequestResponseId();
        } else { //response
            header.setRequestResponseId(inRequestResponseId);
        }
        return header;
    }

    public XMLGregorianCalendar getXMLGregorianTime(Date inDate) {
        //Date date = Calendar.getInstance().getTime();
        XMLGregorianCalendar xmlDate;
        try {
            if(inDate) {
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(inDate);
                xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
                //LOGGER.debug("xmlDate : " + xmlDate);
                return xmlDate;
            }

        } catch (Exception e) {
            LOGGER.error("Exception in getXMLGregorianTime : "+ e.getMessage());
        }
        return null;
    }

    public void convertN4UpdateToTloNotification(String inNotificationType, Map inParamMap) {
        try {
            LOGGER.debug("convertN4UpdateToTloNotification BEGIN");

            if (TEXT_NOTIFICATION__RAILCARTYPE.equals(inNotificationType)) {
                RailcarTypeNotification railcarTypeNotification = new RailcarTypeNotification();
                railcarTypeNotification.setHeader(buildHeader(null));
                railcarTypeNotification.setRailcarType(frameRailcarTypeObject(inParamMap.get(RAILCARTYPE_OBJECT)));
                pushOutboundJmsMessage(jaxbToXml(Class.forName("com.syncrotess.external.n4_tlo.RailcarTypeNotification"), railcarTypeNotification), INTEGRATION_SERVICE__N4TOTLO);

            } else if (TEXT_NOTIFICATION__RAILCAR.equals(inNotificationType)) {
                RailcarNotification railcarNotification = new RailcarNotification();
                railcarNotification.setHeader(buildHeader(null));
                Railcars railcars = new Railcars();
                railcars.getRailcar().add(frameRailcarObject(inParamMap.get(RAILCAR_VISIT_OBJECT)));
                railcarNotification.setRailcars(railcars);
                pushOutboundJmsMessage(jaxbToXml(Class.forName("com.syncrotess.external.n4_tlo.RailcarNotification"), railcarNotification), INTEGRATION_SERVICE__N4TOTLO);

           /* } else if (TEXT_NOTIFICATION__RAILCAR_CONSIST.equals(inNotificationType)) { //@TODO: when to trigger
                RailcarConsistNotification railcarConsistNotification = new RailcarConsistNotification();
                railcarConsistNotification.setHeader(buildHeader(null));
                RailcarConsists railcarConsists = new RailcarConsists();
                railcarConsists.getRailcarConsist().add(inParamMap.get(RAILCAR_CONSIST_OBJECT));
                railcarConsistNotification.setRailcarConsists();
                pushOutboundJmsMessage(jaxbToXml(Class.forName("com.syncrotess.external.n4_tlo.RailcarConsistNotification"), railcarConsistNotification), INTEGRATION_SERVICE__N4TOTLO);*/

            } else if (TEXT_NOTIFICATION__CONTAINER_DELETE.equals(inNotificationType)) {
                UnitFacilityVisit ufv = inParamMap.get(UFV_OBJECT);
                ContainerDeleteNotification containerDeleteNotification = new ContainerDeleteNotification();
                containerDeleteNotification.setHeader(buildHeader(null));
                containerDeleteNotification.setContainerId(ufv.getPrimaryEqId());
                pushOutboundJmsMessage(jaxbToXml(Class.forName("com.syncrotess.external.n4_tlo.ContainerDeleteNotification"), containerDeleteNotification), INTEGRATION_SERVICE__N4TOTLO);

            } else if (TEXT_NOTIFICATION__CONTAINER_UPDATE.equals(inNotificationType)) {
                ContainerUpdateNotification containerUpdateNotification = new ContainerUpdateNotification();
                containerUpdateNotification.setHeader(buildHeader(null));
                containerUpdateNotification.setContainer(frameContainerObject(inParamMap.get(UFV_OBJECT)));
                pushOutboundJmsMessage(jaxbToXml(Class.forName("com.syncrotess.external.n4_tlo.ContainerUpdateNotification"), containerUpdateNotification), INTEGRATION_SERVICE__N4TOTLO);

            } else if (TEXT_NOTIFICATION__CONTAINER_TRANSIT_DELETE.equals(inNotificationType)) {
                WorkInstruction workInstruction = inParamMap.get(WI_OBJECT);
                ContainerTransitDeleteNotification containerTransitDeleteNotification = new ContainerTransitDeleteNotification();
                containerTransitDeleteNotification.setHeader(null);
                containerTransitDeleteNotification.setContainerTransitId(workInstruction.getWiGkey());
                pushOutboundJmsMessage(jaxbToXml(Class.forName("com.syncrotess.external.n4_tlo.ContainerTransitDeleteNotification"), containerTransitDeleteNotification), INTEGRATION_SERVICE__N4TOTLO);

            } else if (TEXT_NOTIFICATION__CONTAINER_TRANSIT_UPDATE.equals(inNotificationType)) {
                ContainerTransitUpdateNotification containerTransitUpdateNotification = new ContainerTransitUpdateNotification();
                containerTransitUpdateNotification.setHeader(null);
                containerTransitUpdateNotification.setContainerTransit(frameContainerTransitObject(inParamMap.get(WI_OBJECT)));
                pushOutboundJmsMessage(jaxbToXml(Class.forName("com.syncrotess.external.n4_tlo.ContainerTransitUpdateNotification"), containerTransitUpdateNotification), INTEGRATION_SERVICE__N4TOTLO);

            } else if (TEXT_NOTIFICATION__SUBTRAIN_NOTIFICATION.equals(inNotificationType)) {
                SubTrainNotification subTrainNotification = new SubTrainNotification();
                subTrainNotification.setHeader(buildHeader(null));
                subTrainNotification.setSubTrain(frameTrainVisitDetailsObject(inParamMap.get(TRAIN_VISIT_DETAILS_OBJECT)));
                pushOutboundJmsMessage(jaxbToXml(Class.forName("com.syncrotess.external.n4_tlo.SubTrainNotification"), subTrainNotification), INTEGRATION_SERVICE__N4TOTLO);

            } else if (TEXT_NOTIFICATION__T3_DONE.equals(inNotificationType)) {
            }

        } catch (Exception e) {
            LOGGER.error("Exception in convertN4UpdateToTloNotification : " + e.getMessage());
        }
    }


    public SubTrain frameTrainVisitDetailsObject(TrainVisitDetails trainVisitDetails) {
        try {
            SubTrain subTrain = new SubTrain();
            if (trainVisitDetails.getCvdCv()) {
                subTrain.setId(trainVisitDetails.getCvdCv().getCvId());
                CarrierVisitPhaseEnum visitPhaseEnum = trainVisitDetails.getCvdCv().getCvVisitPhase();
                if (visitPhaseEnum == CarrierVisitPhaseEnum.INBOUND || visitPhaseEnum == CarrierVisitPhaseEnum.ARRIVED) {
                    subTrain.setStatus(SubTrainStatus.PLANNED);
                } else if (visitPhaseEnum == CarrierVisitPhaseEnum.WORKING) {
                    subTrain.setStatus(SubTrainStatus.LOADING);
                } else if (visitPhaseEnum == CarrierVisitPhaseEnum.COMPLETE) {
                    subTrain.setStatus(SubTrainStatus.COMPLETE);
                } else if (visitPhaseEnum == CarrierVisitPhaseEnum.DEPARTED) {
                    subTrain.setStatus(SubTrainStatus.DEPARTED);
                } else if (visitPhaseEnum == CarrierVisitPhaseEnum.CLOSED) {
                    subTrain.setStatus(SubTrainStatus.CLOSED);
                } else if (visitPhaseEnum == CarrierVisitPhaseEnum.CANCELED) {
                    subTrain.setStatus(SubTrainStatus.CANCELED);
                }
            }

            //LOGGER.debug("ETD: "+trainVisitDetails.getCvdETD());
            if(trainVisitDetails.getCvdETD()) {
                subTrain.setEtd(getXMLGregorianTime(trainVisitDetails.getCvdETD()));
            }
            //@TODO: how to derive?
            subTrain.setLocked(false);
            return subTrain;

        } catch (Exception e) {
            LOGGER.error("Exception in frameRailcarObject : "+e.getMessage() + " : " + e);
        }
    }

    public Railcar frameRailcarObject(RailcarVisit railcarVisit) {
        try {
            com.navis.rail.business.entity.Railcar railcar = railcarVisit.getRcarvRailcar();
            com.navis.rail.business.entity.RailcarType railcarType = railcar.getRcarRailcarTemplateType();
            RailcarDetails railcarDetails = railcar.getRcarRailcarDetails()
            //RailcarVisit railcarVisit = railcar.findRailcarVisit(railcar.getRcarId());

            String ibTrainId = railcarVisit.getCarrierIbVoyNbrOrTrainId();
            String obTrainId = railcarVisit.getCarrierObVoyNbrOrTrainId();
            String inspectionKey = railcarVisit.getRcarvInspectionStatus()? railcarVisit.getRcarvInspectionStatus().getKey() : null;
            boolean isFixedOutboundTrain = (ArgoUtils.isNotEmpty(obTrainId) && !CarrierVisit.isGenericId(obTrainId)? true : false);
            boolean isDamaged = railcarVisit.getRcarvRailcarState()? railcarVisit.getRcarvRailcarState().getRcarsttIsDamaged() : false;

            com.syncrotess.external.n4_tlo.types.Railcar xsdRailcar = new com.syncrotess.external.n4_tlo.types.Railcar();
            xsdRailcar.setId(railcarVisit.getRcarvId());
            xsdRailcar.setRailcarType(railcarType.getRcartypId());
            xsdRailcar.setOwnerRailway(railcar.getRcarOwner().getBzuId());
            xsdRailcar.setDamaged(isDamaged? RailcarDamaged.BAD : RailcarDamaged.GOOD);
            xsdRailcar.setInspected(ArgoUtils.isNotEmpty(inspectionKey));
            xsdRailcar.setInboundSubTrain(ibTrainId);
            xsdRailcar.setOutboundSubTrain(obTrainId)
            xsdRailcar.setFixedOutboundSubTrain(isFixedOutboundTrain);
            xsdRailcar.setDestination(railcarVisit.getRcarvDestination());
            xsdRailcar.setLocked(false); //@TODO: derive this value

            TrackPosition xsdPosition = new TrackPosition();
            xsdPosition.setTrack(railcarVisit.getRcarvTrack()); //Need to double check
            //xsdPosition.setSequenceNumber()
            Length metermark = new Length();
            //LOGGER.debug("metermark: "+railcarVisit.getRcarvMeterMark());
            if(railcarVisit.getRcarvMeterMark()) {
                metermark.setValue(railcarVisit.getRcarvMeterMark().doubleValue()); //Need to double check
                metermark.setUnit(LengthUnit.MM);
                xsdPosition.setMetermark(metermark);
            } else {
                xsdPosition.setMetermark(null);
            }
            xsdRailcar.setPosition(xsdPosition);

            if(railcarVisit.getRcarvPosition())
                xsdRailcar.setOrientation(railcarVisit.getRcarvPosition().getPosOrientation());
            else
                xsdRailcar.setOrientation(null);

            Platforms xsdPlatforms = new Platforms();
            List<Platform> platformList = new ArrayList<Platform>();
            for(RailcarPlatform rcarPlatform : railcar.getRcarPlatforms()) {
                RailcarPlatformDetails pfDetails = rcarPlatform.getRcarplfRailcarPlatformDetails();

                Platform platform = new Platform();
                platform.setId(pfDetails.getPlfdLabel());
                platform.setType(railcarType.getRcartypName());
                platform.setLockedTiers(TiersLock.NONE); //@TODO: need to check
                platform.setLoadContainers(null); //@TODO: need to check
                platform.setRemainingDischargeContainers(null); //@TODO: need to check
                platform.setConingStatus(ConingStatus.REMOVED); //@TODO: need to check

                TrackPosition platformPosition = new TrackPosition();
                platformPosition.setTrack(TEXT_EMPTY);  //@TODO: need to check
                platformPosition.setSequenceNumber(0) //@TODO: need to check
                //Length length = new Length();
                //LOGGER.debug("length: "+railcarVisit.getRcarvMeterMark());
                if(railcarVisit.getRcarvMeterMark()) {
                    metermark.setValue(railcarVisit.getRcarvMeterMark().doubleValue()); //Need to double check
                    metermark.setUnit(LengthUnit.MM);
                    platformPosition.setMetermark(metermark);
                } else {
                    platformPosition.setMetermark(null);
                }
                platform.setPosition(platformPosition);
                platform.setNumberOfSlots(railcarDetails.getRcdMax20sPerPlatform().shortValue());
                Weight maxNetWeight = new Weight();
                maxNetWeight.setUnit(WeightUnit.KG);
                maxNetWeight.setValue(railcarDetails.getRcdSafeWeightKg());  //@TODO: Need to check
                platform.setMaxNetWeight(maxNetWeight);

                Weight max20FtContainerWeight = new Weight();
                max20FtContainerWeight.setUnit(WeightUnit.KG);
                max20FtContainerWeight.setValue(railcarDetails.getRcdSafeWeightKg()); //@TODO: Need to check
                platform.setMax20FtContainerWeight(max20FtContainerWeight);

                ContainerLengthTypesWithPositions bottomTier = new ContainerLengthTypesWithPositions();
                if(pfDetails.getPlfdIsLower20())
                    bottomTier.getContainerLengthTypeWithPositions().add(loadContainerLengthTypeWithPositions("20"));
                if(pfDetails.getPlfdIsLower40())
                    bottomTier.getContainerLengthTypeWithPositions().add(loadContainerLengthTypeWithPositions("40"));
                if(pfDetails.getPlfdIsLower45())
                    bottomTier.getContainerLengthTypeWithPositions().add(loadContainerLengthTypeWithPositions("45"));
                if(pfDetails.getPlfdIsLower48())
                    bottomTier.getContainerLengthTypeWithPositions().add(loadContainerLengthTypeWithPositions("48"));
                if(pfDetails.getPlfdIsLower53())
                    bottomTier.getContainerLengthTypeWithPositions().add(loadContainerLengthTypeWithPositions("53"));
                platform.setAllowedLengthTypesAndPositionsBottomTier(bottomTier);

                ContainerLengthTypesWithPositions topTier = new ContainerLengthTypesWithPositions();
                if(pfDetails.getPlfdIsUpper20())
                    topTier.getContainerLengthTypeWithPositions().add(loadContainerLengthTypeWithPositions("20"));
                if(pfDetails.getPlfdIsUpper40())
                    topTier.getContainerLengthTypeWithPositions().add(loadContainerLengthTypeWithPositions("40"));
                if(pfDetails.getPlfdIsUpper45())
                    topTier.getContainerLengthTypeWithPositions().add(loadContainerLengthTypeWithPositions("45"));
                if(pfDetails.getPlfdIsUpper48())
                    topTier.getContainerLengthTypeWithPositions().add(loadContainerLengthTypeWithPositions("48"));
                if(pfDetails.getPlfdIsUpper53())
                    topTier.getContainerLengthTypeWithPositions().add(loadContainerLengthTypeWithPositions("53"));
                platform.setAllowedLengthTypesAndPositionsTopTier(topTier);

                platformList.add(platform);
            }
            xsdPlatforms.getPlatform().addAll(platformList);
            xsdRailcar.setPlatforms(xsdPlatforms);
            return xsdRailcar;

        } catch (Exception e) {
            LOGGER.error("Exception in frameRailcarObject : "+e.getMessage() + " : " + e);
        }
    }

    public RailcarType frameRailcarTypeObject(com.navis.rail.business.entity.RailcarType railcarType) {
        try {
            RailcarType xsdRailcarType = new RailcarType();
            xsdRailcarType.setId(railcarType.getRcartypId());

            RailcarDetails rcDetails = railcarType.getRcartypRailcarDetails();
            if (rcDetails != null) {
                if (rcDetails.getRcdSafeWeightKg() != null) {
                    try {
                        Weight maxNetWeight = new Weight();
                        maxNetWeight.setUnit(WeightUnit.KG);
                        maxNetWeight.setValue(rcDetails.getRcdSafeWeightKg());
                        xsdRailcarType.setMaxNetWeight(maxNetWeight);
                    } catch (Exception ex) {
                        LOGGER.error("Exception while setting maxNetWeightType: " + ex.getMessage());
                    }
                }
                if (rcDetails.getRcdTareWeightKg() != null) {
                    try {
                        Weight tareWeightType = new Weight();
                        tareWeightType.setUnit(WeightUnit.KG);
                        tareWeightType.setValue(rcDetails.getRcdTareWeightKg());
                        xsdRailcarType.setTareWeight(tareWeightType);
                    } catch (Exception ex) {
                        LOGGER.error("Exception while setting tareWeightType: " + ex.getMessage());
                    }
                }
                if (rcDetails.getRcdLengthCm() != null) {
                    try {
                        Length xsdLength = new Length();
                        xsdLength.setUnit(LengthUnit.MM);
                        if (rcDetails.getRcdLengthCm())
                            xsdLength.setValue(rcDetails.getRcdLengthCm() * 10);
                        xsdRailcarType.setLength(xsdLength);
                    } catch (Exception ex) {
                        LOGGER.error("Exception while setting lengthType: " + ex.getMessage());
                    }
                }

                //@TODO : how ReeferSuitableBottomTier and ReeferSuitableTopTier will be derived
                xsdRailcarType.setReeferSuitableBottomTier(true);
                xsdRailcarType.setReeferSuitableTopTier(true);

                List<PlatformType> pfTypes = new ArrayList<PlatformType>();
                for (Object obj : railcarType.getRcartypPlatforms()) {
                    try {
                        //LOGGER.debug("obj: "+obj);
                        RailcarTypePlatform rcTypePlatform = (RailcarTypePlatform) obj;
                        RailcarPlatformDetails pfDetails = rcTypePlatform.getRcartyplfRailcarPlatformDetails();
                        //LOGGER.debug("pfDetails: "+pfDetails);

                        PlatformType pType = new PlatformType();
                        pType.setId(pfDetails.getPlfdSequence().toString() + pfDetails.getPlfdLabel());
                        //@TODO : how number of slots calculated
                        Long max20sPerPlatform = rcDetails != null ? rcDetails.getRcdMax20sPerPlatform() : null;
                        pType.setNumberOfSlots(max20sPerPlatform.shortValue());
                        //LOGGER.debug("pType: "+pType);

                        //@TODO : how well calculated
                        pType.setWell(true);

                        if (pfDetails.getPlfdFloorHeightCm() != null) {
                            Length length = new Length();
                            length.setUnit(LengthUnit.MM);
                            //@TODO: Can we consider floorheight for deckHeight?
                            length.setValue(pfDetails.getPlfdFloorHeightCm() * 10); //converted to MM
                            pType.setDeckHeight(length);
                        }
                        //LOGGER.debug("pfDetails: "+pfDetails);

                        //@TODO: MaxNetWeight and Max20FtContainerWeight - what are the matching N4 values?
                        /*if (pfDetails.getPlfdTareWeightKg() != null) {
                            Weight maxNetWeight = new Weight();
                            maxNetWeight.setUnit(WeightUnit.KG);
                            maxNetWeight.setValue(0.0); //
                            pType.setMaxNetWeight(maxNetWeight);
                        }*/
                        if (rcDetails.getRcdTareWeightKg() != null) {
                            Weight tareWeight = new Weight();
                            tareWeight.setUnit(WeightUnit.KG);
                            tareWeight.setValue(rcDetails.getRcdTareWeightKg());
                            pType.setTareWeight(tareWeight);
                        }
                        if (pfDetails.getPlfdWeightMax20InKg() != null) {
                            Weight max20FtContainerWeight = new Weight();
                            max20FtContainerWeight.setUnit(WeightUnit.KG);
                            max20FtContainerWeight.setValue(pfDetails.getPlfdWeightMax20InKg());
                            pType.setMax20FtContainerWeight(max20FtContainerWeight);
                        }
                        pfTypes.add(pType);

                    } catch (Exception ex) {
                        LOGGER.error("Exception while setting platform: " + ex.getMessage());
                    }
                }
                PlatformTypes platformTypes = new PlatformTypes();
                platformTypes.getPlatformType().addAll(pfTypes);
                xsdRailcarType.setPlatformTypes(platformTypes);

                return xsdRailcarType;
            }

        } catch (Exception e) {
            LOGGER.error("Exception in frameRailcarTypeObject : "+e.getMessage() + " : " + e);
        }
    }

    public ContainerTransit frameContainerTransitObject(WorkInstruction workInstruction) {
        try {
            ContainerTransit containerTransit = new ContainerTransit();
            if(workInstruction.getWiGkey())
                containerTransit.setId(workInstruction.getWiGkey().toString());
            containerTransit.setContainerId(workInstruction.getWiUfv().getUfvUnit().getUnitId());
            containerTransit.setFromLocation(workInstruction.getWiFromPosition().getPosLocId());
            containerTransit.setToLocation(workInstruction.getWiToPosition().getPosLocId());
            if(workInstruction.getWiMoveStage() != null)
                containerTransit.setStatus(ContainerTransitStatus.fromValue(workInstruction.getWiMoveStage().getKey()));
            return containerTransit;

        } catch (Exception e) {
            LOGGER.error("Exception in frameContainerTransitObject : "+e.getMessage() + " : " + e);
        }
    }

    public Container frameContainerObject(UnitFacilityVisit ufv) {
        try {
            if(ufv == null)
                return null;

            Unit unit = ufv.getUfvUnit();
            Equipment equipment = unit.getUnitEquipment();
            EquipType equipType = equipment.getEqEquipType();

            Container container = new Container();
            container.setId(unit.getUnitId());
            String equipLength = equipType.getEqtypBasicLength().getKey();
            if (equipLength.length() > 5)
                container.setLengthType(equipLength.substring(5)); //@TODO: basicLength or nominalLength?

            Weight tareWeight = new Weight();
            tareWeight.setUnit(WeightUnit.KG);
            tareWeight.setValue(equipment.getEqTareWeightKg());
            container.setTareWeight(tareWeight);

            Length height = new Length();
            height.setUnit(LengthUnit.MM);
            if(equipment.getEqHeightMm())
                height.setValue(equipment.getEqHeightMm().doubleValue());
            container.setHeight(height);

            container.setIsoCode(equipType.getEqtypId());
            container.setReefer((EquipRfrTypeEnum.NON_RFR != equipType.getEqtypRfrType()));
            container.setTank(equipType.isTank());
            container.setFlatrack(equipType.isFlat());
            container.setOverheightOpenTop(equipType.isOpen());
            container.setWellSuitable(false); //@TODO: Need to check

            container.setEmpty(FreightKindEnum.MTY.equals(unit.getUnitFreightKind()))
            //@TODO: Do we need to check category for STORAGE as well?

            Weight grossWeight = new Weight();
            grossWeight.setUnit(WeightUnit.KG);
            if (unit.getUnitGoodsAndCtrWtKgVerfiedGross()) {
                grossWeight.setValue(unit.getUnitGoodsAndCtrWtKgVerfiedGross());
            }
            container.setGrossWeight(grossWeight);

            if (unit.getUnitGoods()) {
                container.setHazardous(unit.getUnitGoods().getGdsIsHazardous());
                container.setDestination(unit.getUnitGoods().getGdsDestination());
            } else {
                container.setHazardous(false);
                container.setDestination(TEXT_EMPTY);
            }
            container.setOog(unit.getUnitIsOog());

            if (ufv.getUfvLastKnownPosition()) {
                container.setCurrentLocation(ufv.getUfvLastKnownPosition().toString());
            }
            if (ufv.getUfvActualObCv() && LocTypeEnum.RAILCAR == ufv.getUfvActualObCv().getCvCarrierMode()) {
                TrainVisitDetails tvd = TrainVisitDetails.resolveTvdFromCv(ufv.getUfvActualObCv());
                if (tvd && tvd.getRvdtlsRR())
                    container.setRailway(tvd.getRvdtlsRR().getBzuId());
            }
            if(ufv.getUfvTimeIn())
                container.setArrivalTime(getXMLGregorianTime(ufv.getUfvTimeIn())); //@TODO: which time needs to be consider?

            if(ufv.getFinalPlannedPosition()) {
                container.setToLocation(ufv.getFinalPlannedPosition().getPosSlot());
            }

            container.setPairable(false); //@TODO: How to derive?
            container.setPairedWith(TEXT_EMPTY); //@TODO: How to derive?
            container.setOnlyGroundSlot(true);  //@TODO: How to derive?
            container.setStackable(equipType.isFlat()); //@TODO: Is this correct?
            container.setPriority((short) 1); //@TODO: How to derive?
            container.setLockedOnSlot(Boolean.TRUE); //@TODO: How to derive?
            container.setSpecialStow1(SpecialStowType.ONLY_GROUNDSLOT); //@TODO: to be derived
            container.setDangerousGoodsClass(unit.getUnitGoods().getGdsImdgTypes());
            return container;

        } catch (Exception e) {
            LOGGER.error("Exception in frameContainerObject : "+e.getMessage());
        }
    }


    private ContainerLengthTypeWithPositions loadContainerLengthTypeWithPositions(String feetSize) {
        ContainerLengthTypeWithPositions typeWithPositions = new ContainerLengthTypeWithPositions();
        typeWithPositions.setContainerLengthType(feetSize);
        Length length = new Length();
        length.setValue(0.0D); //@TODO: need to check
        length.setUnit(LengthUnit.MM);
        typeWithPositions.setMetermark(length);
        typeWithPositions.setTrack(""); //@TODO: need to check

        PlatformSlotPosition slotPosition = new PlatformSlotPosition();
        slotPosition.setSlot(1); //@TODO: Assume that the 20ft contaier always in first slot. If this logic is not correct, how to derive it?
        typeWithPositions.getPlatformSlotPosition().add(slotPosition);
        if(Integer.parseInt(feetSize) >= 40) {
            slotPosition.setSlot(2); //@TODO: Assume that the 20ft contaier always in first slot. If this logic is not correct, how to derive it?
            typeWithPositions.getPlatformSlotPosition().add(slotPosition);
        }
        return typeWithPositions;
    }

    public boolean isRailContainer(UnitFacilityVisit inUfv) {
        if(inUfv && inUfv.getUfvActualObCv()) {
            CarrierVisit obCv = inUfv.getUfvActualObCv();
            if (("GEN_TRAIN" == obCv.getCvId() || LocTypeEnum.TRAIN == obCv.getCvCarrierMode()) && inUfv.isTransitStateAtMost(UfvTransitStateEnum.S40_YARD)) {
                return true;
            }
        }
        return false;
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
    public static final String INTEGRATION_SERVICE__PONG = "pongqueue";

    public static final String TEXT_NOTIFICATION__RAILCARTYPE = "RailcarTypeNotification";
    public static final String TEXT_NOTIFICATION__RAILCAR = "RailcarNotification";
    public static final String TEXT_NOTIFICATION__RAILCAR_CONSIST = "RailcarConsistNotification";
    public static final String TEXT_NOTIFICATION__CONTAINER_DELETE = "RailcarConsistNotification";
    public static final String TEXT_NOTIFICATION__CONTAINER_UPDATE = "ContainerUpdate";
    public static final String TEXT_NOTIFICATION__CONTAINER_TRANSIT_DELETE = "ContainerTransitDelete";
    public static final String TEXT_NOTIFICATION__CONTAINER_TRANSIT_UPDATE = "ContainerTransitUpdate";
    public static final String TEXT_NOTIFICATION__SUBTRAIN_NOTIFICATION = "SubTrain";
    //public static final String TEXT_NOTIFICATION__TRAIN_LOAD_PLAN = "TrainLoadPlan"; //TLO to N4
    public static final String TEXT_NOTIFICATION__T3_DONE = "T3Done";

    public static final String UFV_OBJECT = "ufvObj";
    public static final String WI_GKEY    = "wiGkey";
    public static final String CONTAINER_ID  = "containerId";
    public static final String FROM_LOCATION = "fromLocation";
    public static final String TO_LOCATION   = "toLocation";
    public static final String WI_STAGE      = "status";
    public static final String WI_OBJECT = "workinstructionObject";
    public static final String RAILCARTYPE_OBJECT = "railcartypeObject";
    public static final String RAILCAR_OBJECT = "railcarObject";
    public static final String RAILCAR_VISIT_OBJECT = "railcarVisitObject";
    public static final String RAILCAR_CONSIST_OBJECT = "railcarConsistObject";
    public static final String TRAIN_VISIT_DETAILS_OBJECT = "trainVisitDetailsObject";


    public static final String TEXT_EMPTY = "";
    public static final String TEXT_TRUE = "true";
    public static final String SCALE_UNIT_CM = "cm";
    public static final String SCALE_UNIT_KG = "kg";
    public static final String SCALE_UNIT_MM = "mm";

    public static final String REQUEST_MESSAGE_CLASS = "com.syncrotess.external.n4_tlo.common.types.RequestMessage";

    public static final String RAILCARTYPE_RESPONSE_CLASS = "com.syncrotess.external.n4_tlo.RailcarTypeResponse";
    public static final String RAILCAR_RESPONSE_CLASS = "com.syncrotess.external.n4_tlo.RailcarResponse";
    public static final String CONTAINER_DELETE_CLASS = "com.syncrotess.external.n4_tlo.ContainerDeleteNotification";
    public static final String CONTAINER_RESPONSE_CLASS = "com.syncrotess.external.n4_tlo.ContainerResponse";
    private static final String CONTAINERTRANSIT_RESPONSE_CLASS = "com.syncrotess.external.n4_tlo.ContainerTransitResponse";
    private static final String SUBTRAIN_RESPONSE_CLASS = "com.syncrotess.external.n4_tlo.SubTrainResponse";
    public static final String PING_RESPONSE_CLASS = "com.syncrotess.external.n4_tlo.PingResponse";

    private static final int DB_CHAR_LIMIT = 3000;

    private static final String HEADER_SENDER_ID = "N4";
    private static final String HEADER_RECEIVER_ID = "TLO";
    private static final String HEADER_INTERFACE_ID = "1.0";

    private Logger LOGGER = Logger.getLogger(TLOAdaptor.class);
}