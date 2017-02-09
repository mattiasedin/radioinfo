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

    public GetSchedulesBackgroundWorker(ActionListener listener, int channelId) {
        super(listener, String.format(EndpointAPI.SCHEDULE, channelId), Scheduledepisode.class);
        this.channelId = channelId;
    }

    @Override
    protected ArrayList getData(String url) throws Exception {
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
                String pastStr = formatter.format(datePast).toString();
                ArrayList<Scheduledepisode> dataBefore = getReader().getDataListFromUri(String.format(EndpointAPI.SCHEDULE_FOR_TIME, channelId, pastStr));
                data.addAll(0, dataBefore);
            }

            if (lastEpisode == null) {
                String futureStr = formatter.format(dateFuture).toString();
                ArrayList<Scheduledepisode> dataAfter = getReader().getDataListFromUri(String.format(EndpointAPI.SCHEDULE_FOR_TIME, channelId, futureStr));
                data.addAll(dataAfter);
            }
            return data;

        } else {
            return null;
        }
    }

    private Scheduledepisode getEpisodeOnTime(Date currentTime, ArrayList<Scheduledepisode> list) {
        Scheduledepisode currentEpisode = null;
        for (int i = 0; i < list.size(); i++) {
            boolean hasStarted = list.get(i).getStarttimeutc().after(currentTime);
            boolean hasEnded = list.get(i).getEndtimeutc().before(currentTime);
            if (hasStarted && !hasEnded) {
                currentEpisode = list.get(i);
                break;
            }
        }
        return currentEpisode;
    }
}
