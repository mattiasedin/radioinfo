package Models;

import Endpoint.GetImageBackgroundWorker;

import javax.swing.*;
import javax.xml.soap.Node;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.HashMap;

/**
 * Created by mattias on 1/11/17.
 */
public abstract class DataModel {


    public abstract String UrlToImage();



    private ImageIcon icon;

    private ActionListener propagateBackListener;

    private ActionListener imageLoadedListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Image img = (Image) actionEvent.getSource();
            icon = new ImageIcon(img.getScaledInstance(200,200, Image.SCALE_DEFAULT));
            propagateBackListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "OK"));
        }
    };

    @TableDisplay(visible = true, order = 0, name = "Icon")
    public ImageIcon getImageIcon() {
        if (UrlToImage() != null && icon == null) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new GetImageBackgroundWorker(imageLoadedListener, UrlToImage()).execute();
                }
            });
        } else if (icon != null) {
            return icon;
        }
        return new ImageIcon();
    }

    public void setDataChangedListener(ActionListener listener) {
        propagateBackListener = listener;
    }
}
