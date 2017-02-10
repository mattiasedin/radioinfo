package Views;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Created by mattias on 2016-01-07.
 * <p>
 * Renders a loading view with animated loading.
 */
public class LoadingView extends JPanel {

    /**
     * Constructor for the view.
     */
    public LoadingView() {
        this.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.ipady = 20;

        ClassLoader cldr = this.getClass().getClassLoader();
        URL imageURL   = cldr.getResource("loader.gif");

        if (imageURL != null) {
            ImageIcon imageIcon = new ImageIcon(imageURL);
            JLabel iconLabel = new JLabel(imageIcon, JLabel.CENTER);
            imageIcon.setImageObserver(iconLabel);
            panel.add(iconLabel, gbc);
        }

        JLabel label2 = new JLabel("Loading...");
        panel.add(label2, gbc);

        this.add(panel, BorderLayout.CENTER);
    }
}
