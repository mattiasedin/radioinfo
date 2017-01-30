package Endpoint;

import Exceptions.DataDoesNotMatchModelException;
import Exceptions.InvalidUrlException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        try {
            try {
                ArrayList<T> dataListFromUri = dr.getDataListFromUri(dataUri);
                return (U) dataListFromUri;
            } catch (DataDoesNotMatchModelException | InvalidUrlException e) {
                failedException = e;
            }
        }catch (Exception e) {
            System.out.print("error");
        }
        return null;
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
