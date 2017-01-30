package Endpoint;

import Exceptions.DataDoesNotMatchModelException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

/**
 * Created by mattias on 2016-01-08.
 */
public class GetImageBackgroundWorker extends SwingWorker<Image, Integer> {

    private ActionListener listener;
    private String dataUri;
    private boolean failed = false;

    public GetImageBackgroundWorker(ActionListener listener, String dataUri) {
        this.listener = listener;
        this.dataUri = dataUri;
    }

    @Override
    protected Image doInBackground() throws Exception {
        try {
            return EndpointImageDownloader.getImageFromUri(dataUri);
        } catch (DataDoesNotMatchModelException | MalformedURLException e) {
            failed = true;
        }
        return null;
    }

    protected void done() {
        if (!failed) {
            try {
                listener.actionPerformed(new ActionEvent(get(), ActionEvent.ACTION_PERFORMED, "OK"));
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            listener.actionPerformed(new ActionEvent("An error has occurred, please try again", ActionEvent.ACTION_PERFORMED, "Error"));
        }
    }
}
