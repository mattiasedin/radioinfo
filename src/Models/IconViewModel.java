package Models;

import Endpoint.GetImageBackgroundWorker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mattias on 1/11/17.
 * <p>
 * View model for a model. This class has an icon that will be downloaded and scaled to a specific size. When the icon
 * getIcon() is called for first time, the icon will be downloaded and saved in memory for further use. Only one download is
 */
public abstract class IconViewModel {
    private ImageIcon icon;
    private int iconSize = 100;
    private boolean hasDownloadStarted = false;
    private ActionListener propagateBackListener;

    /**
     * Action called when download is complete.
     */
    private final ActionListener iconDownloadedCallback = actionEvent -> {
        Image img = (Image) actionEvent.getSource();
        icon = new ImageIcon(img.getScaledInstance(iconSize, iconSize, Image.SCALE_DEFAULT));
        propagateBackListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "OK"));
    };

    /**
     * Get the icon internet url.
     * @return the url to the image.
     */
    public abstract String getIconUrl();

    /**
     * Gets the image icon. If the icon is not downloaded the download will be initiated and the download listener will
     * be called once the download is done.
     * <p>
     * Be sure to set the download listener before calling this method for the first time.
     * @return the image icon
     */
    @TableDisplay(order = 0, columnName = "Icon")
    public ImageIcon getIcon() {
        if (getIconUrl() != null && icon == null && !hasDownloadStarted) {
            hasDownloadStarted = true;
            SwingUtilities.invokeLater(() -> new GetImageBackgroundWorker(iconDownloadedCallback, getIconUrl()).execute());
        } else if (icon != null) {
            return icon;
        }
        return new ImageIcon();
    }

    /**
     * Sets the download callback listener.
     * @param listener the listener to set.
     */
    public void setIconDownloadedListener(ActionListener listener) {
        propagateBackListener = listener;
    }

    /**
     * Sets the icon size to scale the image to once downloaded. Calls the downloadListener if the icon is already
     * downloaded.
     * @param iconSize the size of the image to resize to.
     */
    public void setIconSize(int iconSize) {
        this.iconSize = iconSize;
        if (icon != null) {
            icon = new ImageIcon(icon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_DEFAULT));
            if (propagateBackListener != null) {
                propagateBackListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "OK"));
            }
        }
    }

    /**
     * Gets if download has ben initiated
     * @return true if getIcon has been called, false otherwise
     */
    public boolean hasImageDownloaded() {
        return icon != null && hasDownloadStarted;
    }
}
