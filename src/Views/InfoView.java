package Views;

import Models.Program;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mattias on 2016-01-08.
 */
public class InfoView extends JPanel {

    public InfoView(Program p) {
        super(new BorderLayout());

        JPanel stackPanel = new JPanel();
        stackPanel.setLayout(new BoxLayout(stackPanel, BoxLayout.Y_AXIS));
        add(stackPanel, BorderLayout.NORTH);

        stackPanel.add(ViewHelper.withPadding(ViewHelper.toLabel(p.getName(), getFont(), Font.BOLD, 1.5),20,10));
        stackPanel.add(ViewHelper.withPadding(ViewHelper.toLabel("Brodcast info:", getFont(), Font.BOLD),0,10));
        stackPanel.add(ViewHelper.withPadding(ViewHelper.toLabel(p.getBroadcastinfo(), getFont()),0,10,10,10));
        stackPanel.add(ViewHelper.withPadding(ViewHelper.toLabel("Description:", getFont(), Font.BOLD),0,10));
        stackPanel.add(ViewHelper.withPadding(ViewHelper.toLabel(p.getDescription(), getFont()),0,10,10,10));
    }
}
