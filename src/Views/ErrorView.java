package Views;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mattias on 2016-01-08.
 * <p>
 * Renders a error view with a specific message.
 */
public class ErrorView extends JPanel {

    /**
     * Constructor for the view.
     * @param s the message to show.
     */
    public ErrorView(String s) {
        this.setLayout(new BorderLayout());

        JLabel label = new JLabel("<html><p>"+ s +"</p></html>", JLabel.CENTER);

        this.add(label, BorderLayout.CENTER);
    }
}
