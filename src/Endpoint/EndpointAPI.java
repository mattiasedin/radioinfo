package Endpoint;

/**
 * Created by mattias on 1/11/17.
 */
public class EndpointAPI {
    public static final String CHANNELS = "http://api.sr.se/api/v2/channels";
    public static final String PROGRAMS_FOR_CHANNEL = "http://api.sr.se/api/v2/programs/index?channelid=%s";
    public static final String PROGRAM = "http://api.sr.se/api/v2/programs/%s";
    public static final String SCHEDULE = "http://api.sr.se/api/v2/scheduledepisodes?channelid=%s";
    public static final String SCHEDULE_FOR_TIME = "http://api.sr.se/api/v2/scheduledepisodes?channelid=%s&date=%s";
}
