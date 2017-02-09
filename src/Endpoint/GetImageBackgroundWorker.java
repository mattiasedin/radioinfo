package Endpoint;

import Exceptions.InternetConnectionException;
import Exceptions.ModelParseException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

/**
 * Created by mattias on 2016-01-08.
 */
public class GetImageBackgroundWorker extends AbstractBackgroundWorker<Image> {

    public GetImageBackgroundWorker(ActionListener listener, String dataUri) {
        super(listener, dataUri);
    }

    @Override
    protected Image getData(String url) throws Exception {
        return EndpointAPIReader.getImageFromUri(url);
    }
}
