package Endpoint;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

/**
 * Created by mattias on 1/12/17.
 */
public abstract class AbstractDataBackgroundWorker<T> extends SwingWorker<T, Integer> {
    private ActionListener listener;
    private String dataUri;
    private boolean failed = false;


    AbstractDataBackgroundWorker(ActionListener listener, String dataUri) {
        this.listener = listener;
        this.dataUri = dataUri;

    }


    @Override
    protected abstract T doInBackground() throws Exception;

    protected void done() {
        if (!failed) {
            try {
                listener.actionPerformed(new ActionEvent(get(), ActionEvent.ACTION_PERFORMED, "OK"));
            } catch (InterruptedException | ExecutionException e) {
                listener.actionPerformed(new ActionEvent("The operation was canceled, please try again", ActionEvent.ACTION_PERFORMED, "Error"));
            }
        } else {
            listener.actionPerformed(new ActionEvent("An error has occurred, please try again", ActionEvent.ACTION_PERFORMED, "Error"));
        }
    }


/*
    EndpointAPIReader<T> getReader() {
        return dr;
    }
*/
    void setError() {
        failed = true;
    }

    String getUri() {
        return dataUri;
    }
}
