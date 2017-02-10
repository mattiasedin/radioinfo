package Endpoint;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

/**
 * Created by mattias on 2/9/17.
 *
 * Arbritary swing background worker that calls an action listener after the process is complete.
 */
public abstract class AbstractBackgroundWorker<T> extends SwingWorker<T, Integer> {
    private final ActionListener listener;
    private final String dataUri;
    private Exception failedException;

    /**
     * Constructor for the class
     * @param listener listener for the which will be called once operation is complete.
     * @param dataUri url to download data from.
     */
    public AbstractBackgroundWorker(ActionListener listener, String dataUri) {
        this.listener = listener;
        this.dataUri = dataUri;
    }

    /**
     * Should not be overriden in implementing class. Override the getData() method instead this mathod handles the expetion
     * callback if something goes wrong in the operation.
     * @return the downloaded data
     */
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

    /**
     * Called from the main working thread and executes the download. This method should be implemented for the main
     * operation not implement the doInBackground() function.
     * @param url the url to download the data from
     * @return the donwloaded data
     * @throws Exception if something goes wrong while doing the operation. Let the wrapper class handle the exception.
     */
    protected abstract T getData(String url) throws Exception;
}
