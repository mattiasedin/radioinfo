package Views;

import Controller.DataIconTableModel;
import Controller.DataTableModel;
import Controller.DateCellRenderer;
import Controller.MouseClickListener;
import Models.IconViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;

/**
 * Created by mattias on 1/11/17.
 * <p>
 * Renders a table with models extending icon view model for a column with images.
 */
public class TableIconView<T extends IconViewModel> extends JPanel {
    private final JScrollPane scrollPane;
    private final JTable table;
    private final DataTableModel<T> tableModel;
    private ActionListener onListItemClickListener;

    protected static final int ICON_SIZE = 150;

    private final String dateFormat = "HH:ss";


    /**
     * Constructor for view
     * @param tableModel the table model to render the table from.
     * @see IconViewModel
     * @see Models.TableDisplay
     */
    public TableIconView(DataIconTableModel<T> tableModel) {
        super(new BorderLayout());

        this.tableModel = tableModel;

        table = new JTable(tableModel);

        table.addMouseListener(new MouseClickListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                onListItemClickListener.actionPerformed(new ActionEvent(getSelectedItem(), ActionEvent.ACTION_PERFORMED, "OK"));
                validate();
                repaint();
            }
        });

        scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);

        for (int columnIndex : getIconColumns()) {
            setColumnWidth(columnIndex, ICON_SIZE);
        }
        updateColumnsRendering();
    }

    /**
     * Constructor to create the default table model for this class.
     * @param typeParameterClass the model class
     * @param data list of data elements
     * @see Models.TableDisplay
     */
    public TableIconView(Class<T> typeParameterClass, ArrayList<T> data) {
        this(new DataIconTableModel<T>(typeParameterClass, data, ICON_SIZE));
    }

    /**
     * Gets the columns that have the return types of ImageIcon class
     * @return array specifying the columns that has the corresponding type
     * @see ImageIcon
     */
    protected int[] getIconColumns() {
        Stack<Integer> columns = new Stack<Integer>();
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            if (tableModel.getColumnClass(i) == ImageIcon.class) {
                columns.push(i);
            }
        }
        int[] iconColumns = new int[columns.size()];
        int index = 0;
        while (!columns.empty()) {
            iconColumns[index] = columns.pop();
            index++;
        }
        return iconColumns;
    }

    /**
     * Sets a fixed width to a column.
     * @param columnIndex the column index to alter.
     * @param width the fixed width to set the column to.
     */
    public void setColumnWidth(int columnIndex, int width) {
        table.getColumnModel().getColumn(columnIndex).setMinWidth(width);
        table.getColumnModel().getColumn(columnIndex).setMaxWidth(width);
    }

    /**
     * Gets the selected item in view.
     * @return the selected item
     */
    public T getSelectedItem() {
        int selectedIndex = table.getSelectionModel().getLeadSelectionIndex();
        return tableModel.getElementAtRow(selectedIndex);
    }

    /**
     * Sets the data to the table and update the view.
     * @param data the data to set.
     */
    public void setData(ArrayList<T> data) {
        tableModel.setDataList(data);
        tableModel.fireTableDataChanged();
    }

    /**
     * Updates the row height and date renderer for all columns.
     */
    private void updateColumnsRendering()
    {
        if (table.getRowCount() > 0) {
            int row = 0;
            int rowHeight = table.getRowHeight();
            for (int column = 0; column < table.getColumnCount(); column++)
            {
                Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
                rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
                rowHeight = Math.max(rowHeight, ICON_SIZE);

                if (table.getColumnClass(column) == Date.class) {
                    table.getColumnModel().getColumn(column).setCellRenderer(new DateCellRenderer(dateFormat));
                }
            }
            table.setRowHeight(rowHeight);
        }
    }

    /**
     * Sets the click listener for the table. Called when user clicks on a table item.
     * @param onListItemClickListener the listener.
     */
    public void setOnListItemClickListener(ActionListener onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
    }

    /**
     * Scrolls the view to specific element
     * @param index element index to scroll to.
     */
    public void scrollTo(int index) {
        index--;
        if (index <= table.getRowCount() && index > 0) {
            int scrollValue = table.getRowHeight() * index;
            scrollPane.getVerticalScrollBar().setValue(scrollValue);
        }
    }
}
