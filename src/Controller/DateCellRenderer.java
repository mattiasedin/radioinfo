package Controller;

import javax.swing.table.DefaultTableCellRenderer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mattias on 1/12/17.
 */
public class DateCellRenderer extends DefaultTableCellRenderer {
    public DateCellRenderer() {
        super();
    }

    @Override
    public void setValue(final Object value) {
        final SimpleDateFormat sdf = new SimpleDateFormat("HH:ss");

        String strValue = "";
        if(value != null && value instanceof Date){
            strValue = sdf.format(value);
        }
        super.setText(strValue);
    }
}
