package Views;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mattias on 2/10/17.
 */
public class StatusBar extends JPanel {
    private final JLabel statusLabel;
    private final SimpleDateFormat sdf;
    private final String format = "EEE HH:mm:ss";

    public StatusBar() {
        sdf = new SimpleDateFormat(format);
        setBorder(new BevelBorder(BevelBorder.LOWERED));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        statusLabel = new JLabel("<html>status</html>");
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(statusLabel);
    }

    public void setText(String text) {
        statusLabel.setText("<html>"+sdf.format(new Date()) + ": " + text+"</html>");
        invalidate();
        repaint();
    }
}
