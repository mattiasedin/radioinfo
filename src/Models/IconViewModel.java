package Models;

import Endpoint.GetImageBackgroundWorker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mattias on 1/11/17.
 */
public abstract class IconViewModel {

    @TableDisplay(visible = false)
    public abstract String getIconUrl();

    private ImageIcon icon;
    private int iconSize = 100;

    private ActionListener propagateBackListener;

    private ActionListener iconDownloadedCallback =  actionEvent -> {
        Image img = (Image) actionEvent.getSource();
        icon = new ImageIcon(img.getScaledInstance(iconSize, iconSize, Image.SCALE_DEFAULT));
        propagateBackListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "OK"));
    };

    @TableDisplay(visible = true, order = 0, name = "Icon")
    public ImageIcon getIcon() {
        if (getIconUrl() != null && icon == null) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new GetImageBackgroundWorker(iconDownloadedCallback, getIconUrl()).execute();
                }
            });
        } else if (icon != null) {
            return icon;
        }
        return new ImageIcon();
    }

    public void setIconDownloadedListener(ActionListener listener) {
        propagateBackListener = listener;
    }

    public void setIconSize(int iconSize) {
        this.iconSize = iconSize;
    }
}
