package Endpoint;

/**
 * Created by mattias on 1/11/17.
 *
 * Endpoint urls for each api call.
 */
public class EndpointAPI {

    /**
     * Gets all channels
     * @see Models.Channel
     */
    public static final String CHANNELS = "http://api.sr.se/api/v2/channels";

    /**
     * Gets all programs for specific channel.
     * <p>
     * Note: This string has to be formated with an channel id.
     * @see Models.Program
     */
    public static final String PROGRAMS_FOR_CHANNEL = "http://api.sr.se/api/v2/programs/index?channelid=%s";

    /**
     * Gets specific program.
     * <p>
     * Note: This string has to be formated with a program id.
     * @see Models.Program
     */
    public static final String PROGRAM = "http://api.sr.se/api/v2/programs/%s";

    /**
     * Gets specific schedule.
     * <p>
     * Note: This string has to be formated with a channel id.
     * @see Models.Scheduledepisode
     */
    public static final String SCHEDULE = "http://api.sr.se/api/v2/scheduledepisodes?channelid=%s";

    /**
     * Gets schedule for a specific date
     * <p>
     * Note: This string has to be formated with a channel id and date in format "yyyy-MM-dd"
     * @see Models.Scheduledepisode
     */
    public static final String SCHEDULE_FOR_TIME = "http://api.sr.se/api/v2/scheduledepisodes?channelid=%s&date=%s";
}
