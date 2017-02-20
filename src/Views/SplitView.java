package Views;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mattias on 1/11/17.
 * <p>
 * View wrapper that can hold arbitrary number of views in horizontal split
 */
public class SplitView extends JPanel {
    private ContentViewSwitcher[] viewManagers;

    /**
     * Constructor of view
     * @param nrOfViews number of views to hold
     */
    public SplitView(int nrOfViews) {
        super(new GridLayout(0, nrOfViews));

        viewManagers = new ContentViewSwitcher[nrOfViews];

        for (int i = 0; i < viewManagers.length; i++) {
            viewManagers[i] = new ContentViewSwitcher();
            this.add(viewManagers[i]);
        }
    }

    /**
     * Replaces content of chosen view with new content.
     * @param index index of view to update.
     * @param view the content to replace with.
     */
    public void changeViewTo(int index, JPanel view) {
        viewManagers[index].changeViewTo(view);
        this.validate();
        this.repaint();
    }

    /**
     * Clears the specified view.
     * @param i index of view to clear.
     */
    public void clear(int i) {
        viewManagers[i].clear();
    }
}
