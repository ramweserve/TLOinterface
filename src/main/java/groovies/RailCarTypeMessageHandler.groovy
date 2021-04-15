import com.navis.rail.business.entity.RailcarType
import com.syncrotess.external.n4_tlo.RailcarTypeResponse
import com.syncrotess.external.n4_tlo.common.types.Header
import com.syncrotess.external.n4_tlo.types.RailcarTypes
import org.apache.log4j.Level
import org.apache.log4j.Logger

public class RailCarTypeMessageHandler {

    public Object processRequest(List<RailcarType> entityList, Header header, Object inAdaptor) {
        try {
            LOGGER.setLevel(Level.DEBUG);
            LOGGER.debug("processRequest begin");

            List<com.syncrotess.external.n4_tlo.types.RailcarType> railcarTypeTypeList = new ArrayList<com.syncrotess.external.n4_tlo.types.RailcarType>();
            for (RailcarType railcarType : entityList) {
                railcarTypeTypeList.add(inAdaptor.frameRailcarTypeObject(railcarType));
            }
            RailcarTypes railcarTypes = new RailcarTypes();
            LOGGER.debug("railcarTypesType object created");
            railcarTypes.getRailcarType().addAll(railcarTypeTypeList);

            RailcarTypeResponse railcarTypeResponseType = new RailcarTypeResponse();
            railcarTypeResponseType.setHeader(header);
            railcarTypeResponseType.setRailcarTypes(railcarTypes);
            LOGGER.debug("railcarTypeResponseType: " + railcarTypeResponseType);

            LOGGER.debug("inAdaptor: " + inAdaptor);
            return inAdaptor.jaxbToXml(Class.forName(inAdaptor.RAILCARTYPE_RESPONSE_CLASS), railcarTypeResponseType);

        } catch (Exception e) {
            LOGGER.error("Exception in processRequest : " + e.getMessage());
            return null;
        }
    }

    private static final Logger LOGGER = Logger.getLogger(RailCarTypeMessageHandler.class);

}
