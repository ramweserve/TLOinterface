import com.navis.rail.business.entity.RailcarVisit
import com.syncrotess.external.n4_tlo.RailcarResponse
import com.syncrotess.external.n4_tlo.common.types.Header
import com.syncrotess.external.n4_tlo.types.Railcars
import org.apache.log4j.Level
import org.apache.log4j.Logger

public class RailCarMessageHandler {

    public Object processRequest(List<com.navis.rail.business.entity.Railcar> entityList, Header header, Object inAdaptor) {

        try {
            List<com.syncrotess.external.n4_tlo.types.Railcar> railcarList = new ArrayList<com.syncrotess.external.n4_tlo.types.Railcar>();
            LOGGER.setLevel(Level.DEBUG);
            LOGGER.debug("iterate the visit list");

            for (RailcarVisit railcarVisitEntity : entityList) {
                railcarList.add(inAdaptor.frameRailcarObject(railcarVisitEntity));
            }
            RailcarResponse railcarResponse = new RailcarResponse();
            railcarResponse.setHeader(header);
            Railcars railcars = new Railcars();
            railcars.getRailcar().addAll(railcarList);
            railcarResponse.setRailcars(railcars);

            LOGGER.debug("inAdaptor: " + inAdaptor);
            return inAdaptor.jaxbToXml(Class.forName(inAdaptor.RAILCAR_RESPONSE_CLASS), railcarResponse);

        } catch (Exception e) {
            LOGGER.error("Exception in processRequest : " + e.getMessage() + " :: " + e);
            //e.printStackTrace();
            return null;
        }
    }

    private static final Logger LOGGER = Logger.getLogger(RailCarMessageHandler.class);
}