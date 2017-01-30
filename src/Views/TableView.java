package Views;

import Controller.ContentViewManager;
import Controller.DataTableModel;
import Controller.DateCellRenderer;
import Controller.MouseClickListener;
import Models.DataModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mattias on 1/11/17.
 */
public class TableView<T extends DataModel> extends JPanel {
    public JTable table;
    private DataTableModel<T> tableModel;
    private ContentViewManager viewManager;
    private int selectedRow;
    private ActionListener onListItemClickListener;

    public TableView(Class<T> typeParameterClass) {
        super(new BorderLayout());
        tableModel = new DataTableModel<T>(typeParameterClass);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
    }



    public TableView(Class<T> typeParameterClass, ArrayList<T> data) {
        super(new BorderLayout());
        tableModel = new DataTableModel<T>(typeParameterClass, data);
        table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setMinWidth(200);
        table.getColumnModel().getColumn(0).setMaxWidth(200);
        //table.setDefaultRenderer(Date.class, new Controller.DateRenderer());
        //table.getColumnModel().getColumn(1).setCellRenderer(new Controller.DateCellRenderer());

        updateRowHeights(table);
        table.addMouseListener(new MouseClickListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                int row = table.getSelectionModel().getLeadSelectionIndex();
                onListItemClickListener.actionPerformed(new ActionEvent(tableModel.getOfferAtRow(row), ActionEvent.ACTION_PERFORMED, "OK"));
                validate();
                repaint();
            }
        });
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
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
                rowHeight = Math.max(rowHeight, 200);

                if (table.getColumnClass(column) == Date.class) {
                    table.getColumnModel().getColumn(column).setCellRenderer(new DateCellRenderer());
                }
            }
            table.setRowHeight(rowHeight);
        }
    }


    public void setOnListItemClickListener(ActionListener onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
    }
}
