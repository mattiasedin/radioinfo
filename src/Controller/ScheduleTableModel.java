package Controller;

import Models.Scheduledepisode;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mattias on 2/8/17.
 *
 * Specified DataTableModel for the Schedulepisode model. This model extends the table with an additional column to
 * highlight the current episode.
 */
public class ScheduleTableModel extends DataIconTableModel<Scheduledepisode> {
    private final ImageIcon icon;
    private int highlightedRow = -1;
    private final ArrayList<Scheduledepisode> schedules;

    /**
     * Constructor for the table model
     * @param iconSize preferred size of the icon. The table will resize the icon to fit this value by a square.
     * @param highlightIconSize preferred width of the highlighting column.
     * @param datalist the list of data to build the table from
     */
    public ScheduleTableModel(ArrayList<Scheduledepisode> datalist, int iconSize, int highlightIconSize) {
        super(Scheduledepisode.class, datalist, iconSize);

        this.schedules = datalist;

        ClassLoader cldr = this.getClass().getClassLoader();
        URL imageURL   = cldr.getResource("current-selected.png");
        if (imageURL != null) {
            ImageIcon imageIcon = new ImageIcon(imageURL);
            icon = new ImageIcon(imageIcon.getImage().getScaledInstance(highlightIconSize, iconSize, Image.SCALE_DEFAULT));
        } else {
            icon = new ImageIcon();
        }


        updateCurrentEpisode();
    }

    /**
     * Updates the current highlighted row according to the current date.
     */
    public void updateCurrentEpisode() {
        Date currentDate = new Date();
        for(int i = 0; i < schedules.size(); i++) {
            if (schedules.get(i).getStarttimeutc().before(currentDate) && schedules.get(i).getEndtimeutc().after(currentDate)) {
                highlightedRow = i;
                break;
            }
        }
    }

    @Override
    public int getColumnCount() {
        return super.getColumnCount() + 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            if (rowIndex == highlightedRow)
                return icon;
            else
                return null;
        } else
            return super.getValueAt(rowIndex, columnIndex-1);
    }
    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex == 0) {
            return "";
        } else {
            return super.getColumnName(columnIndex-1);
        }
    }

    @Override
    public Class getColumnClass(int column) {
        if (column == 0) {
            return ImageIcon.class;
        } else {
            return super.getColumnClass(column-1);
        }
    }
}
