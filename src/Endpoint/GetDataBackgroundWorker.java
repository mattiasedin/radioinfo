package Endpoint;

import Exceptions.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by mattias on 1/12/17.
 *
 */
public class GetDataBackgroundWorker<T> extends AbstractBackgroundWorker<T> {
    private EndpointAPIReader<T> dr;

    public GetDataBackgroundWorker(ActionListener listener, String dataUri, Class<T> typeParameterClass) {
        super(listener, dataUri);
        this.dr = new EndpointAPIReader<T>(typeParameterClass);
    }

    @Override
    protected T getData(String url) throws Exception {
        return dr.getDataFromUri(url);
    }
}
