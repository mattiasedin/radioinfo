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
public class ScheduleTableModel extends DataTableModel<Scheduledepisode> {
    private ImageIcon icon;
    private int highlightedRow = -1;
    private ArrayList<Scheduledepisode> schedules;

    /**
     * Constructor for the table model
     * @param iconSize prefered size of the icon. The table will resize the icon to fit this value by a square.
     * @param highlightIconSize prefered width of the highlighting column.
     * @param schedules the
     */
    public ScheduleTableModel(int iconSize, int highlightIconSize, ArrayList<Scheduledepisode> schedules) {
        super(Scheduledepisode.class, iconSize, schedules);

        this.schedules = schedules;

        ClassLoader cldr = this.getClass().getClassLoader();
        URL imageURL   = cldr.getResource("current-selected.png");
        ImageIcon imageIcon = new ImageIcon(imageURL);
        icon = new ImageIcon(imageIcon.getImage().getScaledInstance(highlightIconSize, iconSize, Image.SCALE_DEFAULT));

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

    @Override
    public void fireTableCellUpdated(int rowIndex, int columnIndex) {
        if (columnIndex < getColumnCount()-1)
            columnIndex++;
        super.fireTableCellUpdated(rowIndex, columnIndex);
    }
}
