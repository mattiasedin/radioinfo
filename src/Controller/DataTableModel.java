package Controller;

import Models.IconViewModel;
import Models.TableDisplay;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by mattias on 1/11/17.
 *
 * Table model for rendering a table with data structure defined by model with propperly defined TableDisplay annotation.
 * The columns defined by the TableDisplay interface as visible will be shown in the table.
 */
public class DataTableModel<T extends IconViewModel> extends AbstractTableModel {
    private ArrayList<T> dataList;
    private final Method[] getMethods;
    private final int iconSize;

    /**
     * Constructor for the table model.
     * @param typeParameterClass the typed class to render as table. This class has to implement the TableDisplay
     *                           annotation properly. See the documentation fo TableDisplay interface for further
     *                           information and usage.
     * @param iconSize prefered size of the icon. The table will resize the icon to fit this value by a square.
     */
    private DataTableModel(Class<T> typeParameterClass, int iconSize) {
        dataList = new ArrayList<T>();
        this.iconSize = iconSize;

        ArrayList<Method> allVisibleMethods = getVisibleMethods(typeParameterClass);

        Method[] methods = new Method[allVisibleMethods.size()];
        allVisibleMethods.toArray(methods);
        getMethods = doSelectionSort(methods);

    }

    /**
     *Constructor for the table model.
     * @param typeParameterClass the typed class to render as table. This class has to implement the TableDisplay
     *                           annotation properly. See the documentation fo TableDisplay interface for further
     *                           information and usage.
     * @param iconSize prefered size of the icon. The table will resize the icon to fit this value by a square.
     * @param channels the list of data to show
     */
    public DataTableModel(Class<T> typeParameterClass, int iconSize, ArrayList<T> channels) {
        this(typeParameterClass, iconSize);
        this.dataList = channels;
    }

    /**
     * Gets the getters from the typed class. The methods that start with "get" and has no parameters and has the
     * annotation TableDisplay and is market as visible will be returned from this method.
     * @param typeParameterClass the typed class to get methods from.
     * @return getter for the typed class which are marked as visible.
     */
    private ArrayList<Method> getVisibleMethods(Class<T> typeParameterClass) {
        ArrayList<Method>  allVisibleMethods = new ArrayList<>();

        Class<?> c;

        try {
            c = Class.forName( typeParameterClass.getName() );
        } catch (ClassNotFoundException e) {
            return allVisibleMethods;
        }

        Method[] methods = c.getMethods();

        for (Method m: methods) {

            if (m.isAnnotationPresent(TableDisplay.class) && m.getParameterCount() == 0 && m.getName().startsWith("get")) {
                Annotation annotation = m.getAnnotation(TableDisplay.class);
                TableDisplay tableDisplay = (TableDisplay) annotation;

                if (tableDisplay.visible()) {
                    allVisibleMethods.add(m);
                }
            }
        }
        return allVisibleMethods;
    }

    /**
     * Sorts the methods by specified order
     * @param arr unsorted list to sort
     * @return the sorted list
     */
    private Method[] doSelectionSort(Method[] arr){

        for (int i = 0; i < arr.length - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < arr.length; j++)
                if (arr[j].getAnnotation(TableDisplay.class).order() < arr[index].getAnnotation(TableDisplay.class).order())
                    index = j;

            Method smallerNumber = arr[index];
            arr[index] = arr[i];
            arr[i] = smallerNumber;
        }
        return arr;
    }



    /**
     * Gets the model element at specific row
     * @param row the row index
     * @return the element at the index
     */
    public T getElementAtRow(int row) {
        return dataList.get(row);
    }

    /**
     * Sets the table data
     * @param dataList the list of data
     */
    public void setDataList(ArrayList<T> dataList) {
        this.dataList = dataList;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return dataList.size();
    }

    @Override
    public int getColumnCount() {
        return getMethods.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        T o = dataList.get(rowIndex);

        if (getMethods[columnIndex].getReturnType() == ImageIcon.class) {
            IconViewModel model = (IconViewModel) o;
            model.setIconSize(iconSize);
            model.setIconDownloadedListener(actionEvent -> {
                fireTableCellUpdated(rowIndex, columnIndex);
            });
        }

        Object results = null;
        try {
            results = getMethods[columnIndex].invoke(o);
        } catch (IllegalAccessException | InvocationTargetException e) {
            results = "Error accessing data";
        }

        return results;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (getMethods[columnIndex].isAnnotationPresent(TableDisplay.class)) {
            Annotation annotation = getMethods[columnIndex].getAnnotation(TableDisplay.class);
            TableDisplay tableDisplay = (TableDisplay) annotation;
            return tableDisplay.name();
        }
        return getMethods[columnIndex].getName().replace("get", "").toLowerCase();
    }

    @Override
    public Class getColumnClass(int column)
    {
        return getMethods[column].getReturnType();
    }
}
