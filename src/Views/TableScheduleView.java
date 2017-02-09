package Views;

import Controller.DataTableModel;
import Controller.ScheduleTableModel;
import Models.Scheduledepisode;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by mattias on 2/8/17.
 */
public class TableScheduleView extends TableIconView<Scheduledepisode> {

    public TableScheduleView(ArrayList<Scheduledepisode> data) {
        super(new ScheduleTableModel(ICON_SIZE, 50, data));

        setColumnWidth(0, 50);
    }

    @Override
    protected int[] getIconColumns() {
        return new int[] {1};
    }
}
