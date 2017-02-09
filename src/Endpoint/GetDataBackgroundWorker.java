package Endpoint;

import Exceptions.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

/**
 * Created by mattias on 1/12/17.
 */
public class GetDataBackgroundWorker<T> extends SwingWorker<T, Integer> {
    private ActionListener listener;
    private String dataUri;
    private Exception failedException;

    private EndpointAPIReader<T> dr;

    public GetDataBackgroundWorker(ActionListener listener, Class<T> typeParameterClass, String dataUri) {
        this.listener = listener;
        this.dataUri = dataUri;
        this.dr = new EndpointAPIReader<T>(typeParameterClass);
    }

    @SuppressWarnings("unchecked")
    protected T doInBackground() {
        T obj = null;
        try {
            obj = dr.getDataFromUri(dataUri);
        } catch (XMLParseExeption | InternetConnectionException | MalformedURLException | ModelParseException | NodeInstantiationException e) {
            failedException = e;
        }
        return obj;
    }

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
}
