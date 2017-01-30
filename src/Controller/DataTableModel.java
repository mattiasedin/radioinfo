package Controller;

import Models.DataModel;
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
 */
public class DataTableModel<T extends DataModel> extends AbstractTableModel {
    ArrayList<T> dataList;
    private final Class<T> typeParameterClass;
    Method[] getMethods;


    public DataTableModel(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
        dataList = new ArrayList<T>();

        ArrayList<Method>  getMethodsTemp = new ArrayList<>();

        try {
            Class<?> c = Class.forName( typeParameterClass.getName() );

            Method[] methods = c.getMethods();

            String[] displayMethods = new String[0];

            for (Method m: methods) {

                if (m.isAnnotationPresent(TableDisplay.class)) {
                    Annotation annotation = m.getAnnotation(TableDisplay.class);
                    TableDisplay tableDisplay = (TableDisplay) annotation;

                    if (tableDisplay.visible()) {
                        getMethodsTemp.add(m);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Method[] tempListOrdered = new Method[getMethodsTemp.size()];
        Stack<Method> tempListUnOrdered = new Stack<>();
        for (Method m : getMethodsTemp) {
            if (m.isAnnotationPresent(TableDisplay.class)) {
                Annotation annotation = m.getAnnotation(TableDisplay.class);
                TableDisplay tableDisplay = (TableDisplay) annotation;

                if (tableDisplay.order() >= 0) {
                    tempListOrdered[tableDisplay.order()] = m;
                } else {
                    tempListUnOrdered.push(m);
                }
            }
        }

        for (int i = 0; i < getMethodsTemp.size(); i++) {
            if (tempListOrdered[i] == null) {
                tempListOrdered[i] = tempListUnOrdered.pop();
            }
        }



        getMethods = tempListOrdered;

    }
    public DataTableModel(Class<T> typeParameterClass, ArrayList<T> channels) {
        this(typeParameterClass);
        this.dataList = channels;
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

        Object results = null;

        try {
            if (getMethods[columnIndex].getReturnType() == ImageIcon.class) {
                o.setDataChangedListener(actionEvent -> {
                    fireTableCellUpdated(rowIndex, columnIndex);
                });
            }
            results = getMethods[columnIndex].invoke(o);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        finally {
            return results;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {

        if (getMethods[columnIndex].isAnnotationPresent(TableDisplay.class)) {
            Annotation annotation = getMethods[columnIndex].getAnnotation(TableDisplay.class);
            TableDisplay tableDisplay = (TableDisplay) annotation;

            if (tableDisplay.name() != null) {
                return tableDisplay.name();
            }
        }
        return getMethods[columnIndex].getName().replace("get", "").toLowerCase();
    }

    public T getOfferAtRow(int row) {
        return dataList.get(row);
    }

    public void setDataList(ArrayList<T> dataList) {
        this.dataList = dataList;
    }

    @Override
    public Class getColumnClass(int column)
    {
        return getMethods[column].getReturnType();
    }



}
