package Views;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mattias on 2016-01-07.
 */
public class LoadingView extends JPanel {
    public LoadingView() {
        this.setLayout(new BorderLayout());

        ClassLoader cldr = this.getClass().getClassLoader();
        java.net.URL imageURL   = cldr.getResource("loader.gif");
        ImageIcon imageIcon = new ImageIcon(imageURL);

        JLabel iconLabel = new JLabel(imageIcon, JLabel.CENTER);
        imageIcon.setImageObserver(iconLabel);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.ipady = 20;
        JLabel label2 = new JLabel("Loading...");
        panel.add(iconLabel, gbc);
        panel.add(label2, gbc);

        this.add(panel, BorderLayout.CENTER);
    }
}
