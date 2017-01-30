package Views;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mattias on 2016-01-08.
 */
public class ErrorView extends JPanel {
    public ErrorView(String s) {
        this.setLayout(new BorderLayout());

        JLabel label = new JLabel("<html><p>"+ s +"</p></html>", JLabel.CENTER);

        this.add(label, BorderLayout.CENTER);
    }
}
