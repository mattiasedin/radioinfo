package Controller;

import javax.swing.table.DefaultTableCellRenderer;
import java.text.SimpleDateFormat;

/**
 * Created by mattias on 1/12/17.
 */
public class DateRenderer extends DefaultTableCellRenderer {

    public DateRenderer() { // This is a contructor
        DateFormatter formatter = new DateFormatter("yyyy-MM-dd");
    }

    public class DateFormatter extends SimpleDateFormat { //This another class within a class

        public DateFormatter(String pattern) {
            super(pattern);
        }
    }
}
