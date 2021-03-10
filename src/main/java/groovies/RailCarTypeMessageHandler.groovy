package groovies

import com.navis.rail.business.entity.RailcarDetails;

//import Processor.IMessageProcessor;
//import parser.TloException;

import com.navis.rail.business.entity.RailcarPlatformDetails
import com.navis.rail.business.entity.RailcarType
import com.navis.rail.business.entity.RailcarTypePlatform
import com.syncrotess.external.n4_tlo.common.types.Header
import com.syncrotess.external.n4_tlo.request.railcartype.DeckHeightType
import com.syncrotess.external.n4_tlo.request.railcartype.LengthType
import com.syncrotess.external.n4_tlo.request.railcartype.Max20FtContainerWeightType
import com.syncrotess.external.n4_tlo.request.railcartype.MaxNetWeightType
import com.syncrotess.external.n4_tlo.request.railcartype.PlatformTypeType
import com.syncrotess.external.n4_tlo.request.railcartype.PlatformTypesType
import com.syncrotess.external.n4_tlo.request.railcartype.RailcarTypeResponseType
import com.syncrotess.external.n4_tlo.request.railcartype.RailcarTypeType
import com.syncrotess.external.n4_tlo.request.railcartype.RailcarTypesType
import com.syncrotess.external.n4_tlo.request.railcartype.TareWeightType
import org.apache.log4j.Level
import org.apache.log4j.Logger

public class RailCarTypeMessageHandler {

    public Object processRequest(List<RailcarType> entityList, Header header, Object inAdaptor) {
        try {
            LOGGER.setLevel(Level.DEBUG);
            LOGGER.debug("processRequest begin");

            List<RailcarTypeType> railcarTypeTypeList = new ArrayList<RailcarTypeType>();
            for (RailcarType railcarType : entityList) {
                RailcarTypeType typeType = new RailcarTypeType();
                typeType.setId(railcarType.getRcartypId());
                //LOGGER.debug("set RailcarTypeType");

                //@TODO : how ReeferSuitableBottomTier and ReeferSuitableTopTier will be derived
                typeType.setReeferSuitableBottomTier(inAdaptor.TEXT_TRUE);
                typeType.setReeferSuitableTopTier(inAdaptor.TEXT_TRUE);

                RailcarDetails rcDetails = railcarType.getRcartypRailcarDetails();
                if (rcDetails != null) {
                    if (rcDetails.getRcdLengthCm() != null) {
                        try {
                            LengthType lengthType = new LengthType();
                            lengthType.setUnit(inAdaptor.SCALE_UNIT_CM);
                            lengthType.setValue(rcDetails.getRcdLengthCm().toString());
                            typeType.setLength(lengthType);
                        } catch (Exception ex) {
                            LOGGER.error("Exception while setting lengthType: " + ex.getMessage());
                        }
                    }
                    if (rcDetails.getRcdSafeWeightKg() != null) {
                        try {
                            MaxNetWeightType maxNetWeightType = new MaxNetWeightType();
                            maxNetWeightType.setUnit(inAdaptor.SCALE_UNIT_KG);
                            maxNetWeightType.setValue(rcDetails.getRcdSafeWeightKg().toString());
                            typeType.setMaxNetWeight(maxNetWeightType);
                        } catch (Exception ex) {
                            LOGGER.error("Exception while setting maxNetWeightType: " + ex.getMessage());
                        }
                    }
                    if (rcDetails.getRcdTareWeightKg() != null) {
                        try {
                            TareWeightType tareWeightType = new TareWeightType();
                            tareWeightType.setUnit(inAdaptor.SCALE_UNIT_KG);
                            tareWeightType.setValue(rcDetails.getRcdTareWeightKg().toString());
                            typeType.setTareWeight(tareWeightType);
                        } catch (Exception ex) {
                            LOGGER.error("Exception while setting tareWeightType: " + ex.getMessage());
                        }
                    }

                    //LOGGER.debug("set platform");

                    List<PlatformTypeType> pfTypes = new ArrayList<PlatformTypeType>();
                    for (Object obj : railcarType.getRcartypPlatforms()) {
                        try {
                            //LOGGER.debug("obj: "+obj);
                            RailcarTypePlatform rcTypePlatform = (RailcarTypePlatform) obj;
                            RailcarPlatformDetails pfDetails = rcTypePlatform.getRcartyplfRailcarPlatformDetails();
                            //LOGGER.debug("pfDetails: "+pfDetails);

                            PlatformTypeType pType = new PlatformTypeType();
                            pType.setId(pfDetails.getPlfdSequence().toString() + pfDetails.getPlfdLabel());
                            //@TODO : how number of slots calculated
                            Long max20sPerPlatform = rcDetails != null ? rcDetails.getRcdMax20sPerPlatform() : null;
                            pType.setNumberOfSlots(max20sPerPlatform != null ? max20sPerPlatform.toString() : null);
                            //LOGGER.debug("pType: "+pType);

                            //@TODO : how well calculated
                            pType.setWell(inAdaptor.TEXT_TRUE);

                            if (pfDetails.getPlfdFloorHeightCm() != null) {
                                DeckHeightType deckHeightType = new DeckHeightType();
                                deckHeightType.setUnit(inAdaptor.SCALE_UNIT_CM);
                                //@TODO: Can we consider floorheight for deckHeight?
                                deckHeightType.setValue(pfDetails.getPlfdFloorHeightCm().toString());
                                pType.setDeckHeight(deckHeightType);
                            }
                            //LOGGER.debug("pfDetails: "+pfDetails);

                            //@TODO: MaxNetWeight and Max20FtContainerWeight - what are the matching N4 values?
                            if (pfDetails.getPlfdTareWeightKg() != null) {
                                MaxNetWeightType maxNetWeightType = new MaxNetWeightType();
                                maxNetWeightType.setUnit(inAdaptor.SCALE_UNIT_KG);
                                maxNetWeightType.setValue(pfDetails.getPlfdTareWeightKg().toString());
                                pType.setMaxNetWeight(maxNetWeightType);
                            }
                            if (pfDetails.getPlfdWeightMax20InKg() != null) {
                                Max20FtContainerWeightType max20FtContainerWeightType = new Max20FtContainerWeightType();
                                max20FtContainerWeightType.setUnit(inAdaptor.SCALE_UNIT_KG);
                                max20FtContainerWeightType.setValue(pfDetails.getPlfdWeightMax20InKg().toString());
                                pType.setMax20FtContainerWeight(max20FtContainerWeightType);
                            }
                            if (rcDetails.getRcdTareWeightKg() != null) {
                                TareWeightType tareWeightType = new TareWeightType();
                                tareWeightType.setUnit(inAdaptor.SCALE_UNIT_KG);
                                tareWeightType.setValue(rcDetails.getRcdTareWeightKg().toString());
                                pType.setTareWeight(tareWeightType);
                            }
                            pfTypes.add(pType);

                        } catch (Exception ex) {
                            LOGGER.error("Exception while setting platform: " + ex.getMessage());
                        }

                    }
                    PlatformTypesType platformTypesType = new PlatformTypesType();
                    platformTypesType.setPlatformType(pfTypes);
                    typeType.setPlatformTypes(platformTypesType);
                }
                railcarTypeTypeList.add(typeType);
            }
            LOGGER.debug("railcarTypeTypeList: ");

            RailcarTypesType railcarTypesType = new RailcarTypesType();
            LOGGER.debug("railcarTypesType object created");
            railcarTypesType.setRailcarType(railcarTypeTypeList);
            LOGGER.debug("set railcartype");

            RailcarTypeResponseType railcarTypeResponseType = new RailcarTypeResponseType();
            LOGGER.debug("railcarTypeResponseType object created");
            railcarTypeResponseType.setHeader(header);
            LOGGER.debug("set headerType");
            railcarTypeResponseType.setRailcarTypes(railcarTypesType);

            LOGGER.debug("railcarTypeResponseType: " + railcarTypeResponseType);

            Class classRailcarTypeResponseType = Class.forName("com.syncrotess.external.n4_tlo.types.railcartype.RailcarTypeResponseType");
            LOGGER.debug("classRailcarTypeResponseType: " + classRailcarTypeResponseType);

            LOGGER.debug("inAdaptor: " + inAdaptor);
            return inAdaptor.jaxbToXml(classRailcarTypeResponseType, railcarTypeResponseType);

        } catch (Exception e) {
            LOGGER.error("Exception in processRequest : " + e.getMessage());
            return null;
        }
    }

/*    String SCALE_UNIT_CM = "cm";
    String SCALE_UNIT_KG = "kg";
    String TEXT_TRUE = "true";*/

    private static final Logger LOGGER = Logger.getLogger(RailCarTypeMessageHandler.class);

}
