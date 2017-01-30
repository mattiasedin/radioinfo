package Views;

import Controller.ContentViewManager;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mattias on 1/11/17.
 */
public class TripleSplitView extends JPanel {
    private ContentViewManager[] viewManagers = new ContentViewManager[3];
    private boolean shouldFill = true;
    private boolean shouldWeightX = true;

    public TripleSplitView() {
        super(new GridLayout(0, 3));

        viewManagers[0] = new ContentViewManager();
        viewManagers[1] = new ContentViewManager();
        viewManagers[2] = new ContentViewManager();

        this.add(viewManagers[0]);
        this.add(viewManagers[1]);
        this.add(viewManagers[2]);
    }


    public void changeViewTo(int index, JPanel view) {
        viewManagers[index].changeViewTo(view);
        this.validate();
        this.repaint();
    }

    public void clear(int i) {
        viewManagers[i].clear();
    }
}
