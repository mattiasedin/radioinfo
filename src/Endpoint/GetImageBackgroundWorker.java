package Endpoint;

import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by mattias on 2016-01-08.
 * <p>
 * Background worker for downloading an image from a internet url
 */
public class GetImageBackgroundWorker extends AbstractBackgroundWorker<Image> {

    /**
     * Constructor for the class
     * @param listener listener for the which will be called once operation is complete.
     * @param dataUri url to download data from.
     */
    public GetImageBackgroundWorker(ActionListener listener, String dataUri) {
        super(listener, dataUri);
    }

    @Override
    protected Image getData(String url) throws Exception {
        return EndpointAPIReader.getImageFromUri(url);
    }
}
