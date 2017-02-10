package Controller;

import Models.IconViewModel;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by mattias on 2/10/17.
 * <p>
 * Table model that handles icons in the list elements
 */
public class DataIconTableModel<T extends IconViewModel> extends DataTableModel<T> {

    private int iconSize = 150;

    /**
     * Constructor for the table model.
     *
     * @param typeParameterClass the typed class to render as table. This class has to implement the TableDisplay
     *                           annotation properly. See the documentation fo TableDisplay interface for further
     *                           information and usage.
     * @param iconSize           prefered size of the icon. The table will resize the icon to fit this value by a square.
     * @param dataList           the list of data to show
     */
    public DataIconTableModel(Class<T> typeParameterClass, ArrayList<T> dataList, int iconSize) {
        super(typeParameterClass, dataList);
        this.iconSize = iconSize;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (getColumnClass(columnIndex) == ImageIcon.class) {
            T model = getElementAtRow(rowIndex);
            if (!model.hasImageDownloaded()) {
                model.setIconSize(iconSize);
                model.setIconDownloadedListener(actionEvent -> fireTableCellUpdated(rowIndex, columnIndex));
            }
        }
        return super.getValueAt(rowIndex, columnIndex);
    }
}
