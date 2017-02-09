package Views;

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
 */
public class TableIconView<T extends IconViewModel> extends JPanel {
    private JScrollPane scrollPane;
    private JTable table;
    private DataTableModel<T> tableModel;
    private ActionListener onListItemClickListener;

    protected static final int ICON_SIZE = 150;

    private final String dateFormat = "HH:ss";


    public TableIconView(DataTableModel<T> tableModel) {
        super(new BorderLayout());

        this.tableModel = tableModel;

        table = new JTable(tableModel);


        table.addMouseListener(new MouseClickListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                //JTable table = (JTable) mouseEvent.getSource();
                //int row = table.getSelectionModel().getLeadSelectionIndex();
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
        updateRowHeights(table);
    }

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

    public TableIconView(Class<T> typeParameterClass, ArrayList<T> data) {
        this(new DataTableModel<T>(typeParameterClass, ICON_SIZE, data));
    }

    public void setColumnWidth(int columnIndex, int width) {
        table.getColumnModel().getColumn(columnIndex).setMinWidth(width);
        table.getColumnModel().getColumn(columnIndex).setMaxWidth(width);
    }


    public T getSelectedItem() {
        int selectedIndex = table.getSelectionModel().getLeadSelectionIndex();
        return tableModel.getElementAtRow(selectedIndex);
    }

    public void setData(ArrayList<T> data) {
        tableModel.setDataList(data);
        tableModel.fireTableDataChanged();
    }


    private void updateRowHeights(JTable table)
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


    public void setOnListItemClickListener(ActionListener onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
    }



    public void scrollTo(int index) {
        index--;
        if (index <= table.getRowCount() && index > 0) {
            int scrollValue = table.getRowHeight() * index;
            scrollPane.getVerticalScrollBar().setValue(scrollValue);
        }
    }
}
