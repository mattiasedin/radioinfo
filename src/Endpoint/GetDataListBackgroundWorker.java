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
 * <p>
 * Download data that is paginated.
 */
public class GetDataListBackgroundWorker<T, U extends ArrayList<T>> extends AbstractBackgroundWorker<U> {
    private EndpointAPIReader<T> dr;

    /**
     * Constructor for the class
     * @param listener listener for the which will be called once operation is complete.
     * @param dataUri url to download data from.
     * @param typeParameterClass type of data to download, this class have to implement the annotation interface ApiModel
     * @see Models.ApiModel
     */
    public GetDataListBackgroundWorker(ActionListener listener, String dataUri, Class<T> typeParameterClass) {
        super(listener, dataUri);
        this.dr = new EndpointAPIReader<T>(typeParameterClass);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected U getData(String url) throws Exception {
        return (U) dr.getDataListFromUri(url);
    }

    /**
     * Gets the endpoint reader that has been instanciated and ready for useage.
     * @return the endpoint reader.
     */
    public EndpointAPIReader<T> getReader() {
        return dr;
    }
}
