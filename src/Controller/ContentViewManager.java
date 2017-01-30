package Controller;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mattias on 2016-01-07.
 */
public class ContentViewManager extends JPanel {

    private JPanel currentView;

    public ContentViewManager() {
        super(new GridLayout());
    }

    public ContentViewManager(JPanel view) {
        super(new GridLayout());
        currentView = view;
        this.add(view);
    }

    public void changeViewTo(JPanel view) {
        if (currentView != null) {
            this.remove(currentView);
        }
        currentView = view;
        this.add(currentView);
        this.validate();
        this.repaint();
    }

    public void clear() {
        this.removeAll();
        this.updateUI();
    }
}
