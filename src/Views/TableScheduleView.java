package Views;

import Controller.DataIconTableModel;
import Controller.DataTableModel;
import Controller.ScheduleTableModel;
import Models.Scheduledepisode;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by mattias on 2/8/17.
 * <p>
 * Specially defined table view for the Schedule Episode model for showing a marker on current running program.
 */
public class TableScheduleView extends TableIconView<Scheduledepisode> {

    /**
     * Constructor for the view.
     * @param data the data to show.
     */
    public TableScheduleView(ArrayList<Scheduledepisode> data) {
        super(new ScheduleTableModel(data, ICON_SIZE, 50));
        setColumnWidth(0, 50);
    }

    @Override
    protected int[] getIconColumns() {
        return new int[] {1};
    }
}
