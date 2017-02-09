package Endpoint;

import Models.Scheduledepisode;

import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mattias on 2/8/17.
 */
public class GetSchedulesBackgroundWorker extends GetDataListBackgroundWorker<Scheduledepisode, ArrayList<Scheduledepisode>> {
    private int channelId;

    /**
     * Constructor for the class
     * @param listener listener for the which will be called once operation is complete.
     * @see Models.ApiModel
     */
    public GetSchedulesBackgroundWorker(ActionListener listener, int channelId) {
        super(listener, String.format(EndpointAPI.SCHEDULE, channelId), Scheduledepisode.class);
        this.channelId = channelId;
    }

    @Override
    protected ArrayList<Scheduledepisode> getData(String url) throws Exception {
        ArrayList<Scheduledepisode> data = getReader().getDataListFromUri(url);
        if (data != null) {

            Scheduledepisode currentEpisode = getEpisodeOnTime(new Date(), data);

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR, -12);
            Date datePast = cal.getTime();

            cal.add(Calendar.HOUR, 24);
            Date dateFuture = cal.getTime();

            Scheduledepisode firstEpisode = getEpisodeOnTime(datePast, data);
            Scheduledepisode lastEpisode = getEpisodeOnTime(dateFuture, data);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            if (firstEpisode == null) {
                String pastStr = formatter.format(datePast);
                ArrayList<Scheduledepisode> dataBefore = getReader().getDataListFromUri(String.format(EndpointAPI.SCHEDULE_FOR_TIME, channelId, pastStr));
                data.addAll(0, dataBefore);
            }

            if (lastEpisode == null) {
                String futureStr = formatter.format(dateFuture);
                ArrayList<Scheduledepisode> dataAfter = getReader().getDataListFromUri(String.format(EndpointAPI.SCHEDULE_FOR_TIME, channelId, futureStr));
                data.addAll(dataAfter);
            }
            return data;

        } else {
            return null;
        }
    }

    /**
     * Iterates through the list of episodes to find if there exist one with specific time in interval
     * @param currentTime the time to check if exist
     * @param list the list to check in.
     * @return the episode in which has the specific time in its interval.
     */
    private Scheduledepisode getEpisodeOnTime(Date currentTime, ArrayList<Scheduledepisode> list) {
        Scheduledepisode currentEpisode = null;
        for (Scheduledepisode episode : list) {
            boolean hasStarted = episode.getStarttimeutc().after(currentTime);
            boolean hasEnded = episode.getEndtimeutc().before(currentTime);
            if (hasStarted && !hasEnded) {
                currentEpisode = episode;
                break;
            }
        }
        return currentEpisode;
    }
}
