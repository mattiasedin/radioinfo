package Views;

import Controller.ScheduleTableModel;
import Models.Scheduledepisode;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mattias on 2/8/17.
 * <p>
 * Specially defined table view for the Schedule Episode model for showing a marker on current running program.
 */
public class TableScheduleView extends TableIconView<Scheduledepisode> {

    private ArrayList<Scheduledepisode> data;

    /**
     * Constructor for the view.
     * @param data the data to show.
     */
    public TableScheduleView(ArrayList<Scheduledepisode> data) {
        super(new ScheduleTableModel(data, ICON_SIZE, 50));
        this.data = data;
        setColumnWidth(0, 50);
    }

    @Override
    protected int[] getIconColumns() {
        return new int[] {1};
    }

    /**
     * Scroll to current active schedule.
     */
    public void scrollToCurrent() {
        Date currentTime = new Date();
        int currentScheduleIndex = 0;
        for (int i = 0; i < data.size(); i++) {
            boolean hasStarted = data.get(i).getStarttimeutc().after(currentTime);
            boolean hasEnded = data.get(i).getEndtimeutc().before(currentTime);
            if (hasStarted && !hasEnded) {
                currentScheduleIndex = i;
                break;
            }
        }
        scrollTo(currentScheduleIndex);
    }
}
