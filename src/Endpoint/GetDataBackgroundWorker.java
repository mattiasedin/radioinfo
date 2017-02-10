package Endpoint;

import java.awt.event.ActionListener;

/**
 * Created by mattias on 1/12/17.
 * <p>
 * Download data that is not paginated and not in list format.
 */
public class GetDataBackgroundWorker<T> extends AbstractBackgroundWorker<T> {
    private final EndpointAPIReader<T> dr;

    /**
     * Constructor for the class
     * @param listener listener for the which will be called once operation is complete.
     * @param dataUri url to download data from.
     * @param typeParameterClass type of data to download, this class have to implement the annotation interface ApiModel
     * @see Models.ApiModel
     */
    public GetDataBackgroundWorker(ActionListener listener, String dataUri, Class<T> typeParameterClass) {
        super(listener, dataUri);
        this.dr = new EndpointAPIReader<T>(typeParameterClass);
    }

    @Override
    protected T getData(String url) throws Exception {
        return dr.getDataFromUri(url);
    }
}
