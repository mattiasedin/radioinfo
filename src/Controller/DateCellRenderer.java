package Controller;

import javax.swing.table.DefaultTableCellRenderer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mattias on 1/12/17.
 *
 * Table cell renderer that renders the cells with dates with new format
 */
public class DateCellRenderer extends DefaultTableCellRenderer {

    private final String format;

    /**
     * Constructor for date renderer
     * @param format the format to render date to.
     */
    public DateCellRenderer(String format) {
        super();
        this.format = format;
    }

    @Override
    public void setValue(final Object value) {
        final SimpleDateFormat sdf = new SimpleDateFormat(format);
        if(value != null && value instanceof Date){
            String strValue = sdf.format(value);
            super.setText(strValue);
        }
        else {
            super.setText(value.toString());
        }
    }
}
