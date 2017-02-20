package Views;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mattias on 2016-01-07.
 *
 * This is a wrapper for dynamic JPanels. The JPanels can be changed at runtime for a dynamic handling of the content.
 */
public class ContentViewSwitcher extends JPanel {

    private JPanel currentView;

    /**
     * Constructor for view manager with empty view.
     */
    public ContentViewSwitcher() {
        super(new GridLayout());
    }

    /**
     * Changes the content of this wrapper to the defined view. This method also disposes the current view is set.
     * @param view the content to show
     */
    public void changeViewTo(JPanel view) {
        if (currentView != null) {
            this.remove(currentView);
        }
        currentView = view;
        this.add(currentView);
        this.validate();
        this.repaint();
    }

    /**
     * Clears the view and disposes the content of this wrapper. The content will become empty.
     */
    public void clear() {
        currentView = null;
        this.removeAll();
        this.updateUI();
    }
}
