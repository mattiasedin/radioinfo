package Endpoint;

import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by mattias on 2/9/17.
 */
public class TestDataBackgroundWorker<T, U extends ArrayList<T>> extends AbstractBackgroundWorker<U> {
    private EndpointAPIReader<T> dr;

    public TestDataBackgroundWorker(ActionListener listener, String dataUri, Class<T> typeParameterClass) {
        super(listener, dataUri);
        this.dr = new EndpointAPIReader<T>(typeParameterClass);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected U getData(String url) throws Exception {
        return (U) dr.getDataListFromUri(url);
    }
}
