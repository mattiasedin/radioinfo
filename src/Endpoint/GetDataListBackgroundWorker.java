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
public class GetDataListBackgroundWorker<T, U extends ArrayList<T>> extends AbstractBackgroundWorker<U> {
    private EndpointAPIReader<T> dr;

    public GetDataListBackgroundWorker(ActionListener listener, String dataUri, Class<T> typeParameterClass) {
        super(listener, dataUri);
        this.dr = new EndpointAPIReader<T>(typeParameterClass);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected U getData(String url) throws Exception {
        return (U) dr.getDataListFromUri(url);
    }

    public EndpointAPIReader<T> getReader() {
        return dr;
    }
}
