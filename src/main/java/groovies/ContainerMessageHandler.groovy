import com.navis.inventory.business.units.UnitFacilityVisit
import com.syncrotess.external.n4_tlo.ContainerResponse
import com.syncrotess.external.n4_tlo.common.types.Header
import com.syncrotess.external.n4_tlo.types.Containers
import org.apache.log4j.Level
import org.apache.log4j.Logger

public class ContainerMessageHandler {

    public Object processRequest(List<UnitFacilityVisit> ufvList, Header header, Object inAdaptor) {
        try {
            LOGGER.setLevel(Level.DEBUG);
            LOGGER.debug("processRequest BEGIN : " + (ufvList ? ufvList.size() : null));
            List<com.syncrotess.external.n4_tlo.types.Container> containerList = new ArrayList<com.syncrotess.external.n4_tlo.types.Container>();
            for (UnitFacilityVisit ufv : ufvList) {
                containerList.add(inAdaptor.frameContainerObject(ufv));
            }
            ContainerResponse containerResponse = new ContainerResponse();
            containerResponse.setHeader(header);
            Containers containers = new Containers();
            containers.getContainer().addAll(containerList);
            containerResponse.setContainers(containers);

            LOGGER.debug("inAdaptor: " + inAdaptor);
            return inAdaptor.jaxbToXml(Class.forName(inAdaptor.CONTAINER_RESPONSE_CLASS), containerResponse);

        } catch (Exception e) {
            LOGGER.error("Exception in processRequest : " + e.getMessage() + " :: " + e);
            //e.printStackTrace();
            return null;
        }
    }

    private static final Logger LOGGER = Logger.getLogger(ContainerMessageHandler.class);
}
