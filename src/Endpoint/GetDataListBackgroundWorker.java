package Endpoint;

import Exceptions.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by mattias on 1/15/17.
 */
public class GetDataListBackgroundWorker<T, U extends ArrayList<T>> extends SwingWorker<U, Integer> {
    private ActionListener listener;
    private String dataUri;
    private Exception failedException;
    private EndpointAPIReader<T> dr;

    public GetDataListBackgroundWorker(ActionListener listener, Class<T> typeParameterClass, String dataUri) {
        this.listener = listener;
        this.dataUri = dataUri;
        this.dr = new EndpointAPIReader<T>(typeParameterClass);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected U doInBackground() {
        ArrayList<T> dataListFromUri = getDataList();
        if (dataListFromUri != null) {
            return (U) dataListFromUri;
        } else {
            return null;
        }
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

    protected ArrayList<T> getDataList(String uri) {
        try {
            return dr.getDataListFromUri(uri);
        } catch (XMLParseExeption | InternetConnectionException | ModelParseException | MalformedURLException | NodeInstantiationException e ) {
            failedException = e;
        }
        return null;
    }

    protected ArrayList<T> getDataList() {
        return getDataList(dataUri);
    }
}
