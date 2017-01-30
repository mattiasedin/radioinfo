package Views;

import Models.Program;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mattias on 2016-01-08.
 */
public class InfoView extends JPanel {

    public InfoView(Program p) {
        super(new GridBagLayout());


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        this.add(new JLabel(p.getName()), gbc);

        gbc.gridy++;

        //JPanel txtpanel = new JPanel(new GridLayout(0,1));
        this.add(new JLabel("Brodcast info:"), gbc);


        gbc.gridy++;
        gbc.weighty = 2;
        this.add(new JLabel("<html><p>"+ p.getBroadcastinfo() +"</p></html>"), gbc);

        gbc.gridy++;
        gbc.weighty = 1;
        this.add(new JLabel("Description:"), gbc);

        gbc.gridy++;
        gbc.weighty = 10;
        this.add(new JLabel("<html><p>"+p.getDescription() + "</p></html>"), gbc);
    }
}
