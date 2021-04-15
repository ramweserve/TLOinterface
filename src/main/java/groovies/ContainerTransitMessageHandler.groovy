import com.navis.inventory.business.moves.WorkInstruction
import com.syncrotess.external.n4_tlo.ContainerTransitResponse
import com.syncrotess.external.n4_tlo.common.types.Header
import com.syncrotess.external.n4_tlo.types.ContainerTransit
import com.syncrotess.external.n4_tlo.types.ContainerTransits
import org.apache.log4j.Level
import org.apache.log4j.Logger

public class ContainerTransitMessageHandler {

    public Object processRequest(List<WorkInstruction> workInstructionEntityList, Header header, Object inAdaptor) {
        try {
            LOGGER.setLevel(Level.DEBUG);
            List<ContainerTransit> containerTransitList = new ArrayList<ContainerTransit>();
            for (WorkInstruction workInstructionEntity : workInstructionEntityList) {
                containerTransitList.add(inAdaptor.frameContainerTransitObject(workInstructionEntity));
            }
            ContainerTransitResponse containerTransitResponse = new ContainerTransitResponse();
            containerTransitResponse.setHeader(header);
            ContainerTransits containerTransits = new ContainerTransits();
            containerTransits.getContainerTransit().addAll(containerTransitList);
            containerTransitResponse.setContainerTransits(containerTransits);

            return inAdaptor.jaxbToXml(Class.forName(inAdaptor.CONTAINERTRANSIT_RESPONSE_CLASS), containerTransitResponse);

        } catch (Exception e) {
            LOGGER.error("Exception in processRequest : " + e.getMessage() + " :: " + e);
            //e.printStackTrace();
            return null;
        }
    }

    /*private static String getCurrentTimeStamp() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss.SSS");
        return dateFormat.format(date);
    }*/

    private static final Logger LOGGER = Logger.getLogger(ContainerTransitMessageHandler.class);
}
