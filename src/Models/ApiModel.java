package Models;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by mattias on 1/18/17.
 * <p>
 * Annotation interface for the api model element. This class is reflected on the SR API calls. The class should be used
 * as a model wrapper for telling the API-readers hos to parse the api data to typed java objects.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiModel {

    /**
     * The model container element name. This method gets the exact name of the model data in the api. The parser can
     * then traverse the api to find a element with this given name. This could also point to the specific element in
     * the api data if the data does not contain multiple values.
     * @return the container name for the model in the api
     * @see Endpoint.NodeReader
     * @see Endpoint.EndpointAPIReader
     */
    public String container();

    /**
     * Get if the api model contains pagination in terms of multiple api-calls for the whole data set. If true the
     * Endpoint reader should traverse the api for multiple calls to download multiple times. This is done acording to
     * the pagination class.
     * @return
     * @see Pagination
     * @see Endpoint.NodeReader
     * @see Endpoint.EndpointAPIReader
     */
    public boolean pagination() default false;
}
