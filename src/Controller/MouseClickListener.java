package Controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by mattias on 2016-01-08.
 *
 * Simplified MouseListener where only the clicked event has to be implemented.
 */
public abstract class MouseClickListener implements MouseListener {

    @Override
    public abstract void mouseClicked(MouseEvent mouseEvent);

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
