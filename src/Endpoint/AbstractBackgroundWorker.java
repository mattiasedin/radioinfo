package Endpoint;

import Exceptions.InternetConnectionException;
import Exceptions.ModelParseException;
import Exceptions.NodeInstantiationException;
import Exceptions.XMLParseExeption;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by mattias on 2/9/17.
 */
public abstract class AbstractBackgroundWorker<T> extends SwingWorker<T, Integer> {
    private ActionListener listener;
    private String dataUri;
    private Exception failedException;

    public AbstractBackgroundWorker(ActionListener listener, String dataUri) {
        this.listener = listener;
        this.dataUri = dataUri;
    }

    @Override
    protected T doInBackground() {
        T obj = null;
        try {
            obj = getData(dataUri);
        } catch (Exception e) {
            failedException = e;
        }
        return obj;
    }

    @Override
    protected void done() {
        if (failedException == null) {
            try {
                listener.actionPerformed(new ActionEvent(get(), ActionEvent.ACTION_PERFORMED, "Ok"));
            } catch (InterruptedException | ExecutionException e) {
                listener.actionPerformed(new ActionEvent("The operation was canceled, please try again", ActionEvent.ACTION_PERFORMED, "Error"));
            }
        } else {
            listener.actionPerformed(new ActionEvent(failedException.getMessage(), ActionEvent.ACTION_PERFORMED, "Error"));
        }
    }

    protected abstract T getData(String url) throws Exception;
}
