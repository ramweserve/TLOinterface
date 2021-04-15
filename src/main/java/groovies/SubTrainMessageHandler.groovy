import com.navis.rail.business.entity.TrainVisitDetails
import com.syncrotess.external.n4_tlo.SubTrainResponse
import com.syncrotess.external.n4_tlo.common.types.Header
import com.syncrotess.external.n4_tlo.types.SubTrains
import org.apache.log4j.Level
import org.apache.log4j.Logger

public class SubTrainMessageHandler {

    public Object processRequest(List<TrainVisitDetails> tvList, Header header, Object inAdaptor) {
        try {
            LOGGER.setLevel(Level.DEBUG);
            LOGGER.debug("processRequest BEGIN : " + (tvList ? tvList.size() : null));

            SubTrains subTrainList = new SubTrains();
            for (TrainVisitDetails trainVisitDetails : tvList) {
                subTrainList.getSubTrain().add(inAdaptor.frameTrainVisitDetailsObject(trainVisitDetails));
            }
            SubTrainResponse subTrainResponse = new SubTrainResponse();
            subTrainResponse.setHeader(header);
            subTrainResponse.setSubTrains(subTrainList);
            return inAdaptor.jaxbToXml(Class.forName(inAdaptor.SUBTRAIN_RESPONSE_CLASS), subTrainResponse);

        } catch (Exception e) {
            LOGGER.error("Exception in processRequest : " + e.getMessage() + " :: " + e);
            //e.printStackTrace();
            return null;
        }
    }

    private static final Logger LOGGER = Logger.getLogger(SubTrainMessageHandler.class);
}