package Models;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by mattias on 1/18/17.
 * <p>
 * Annotation interface for the api model element. This class is reflected on the SR API calls. The class should be used
 * as a model wrapper for telling the API-readers hos to parse the api data to typed java objects.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) //can use in class only.
public @interface ApiModel {

    /**
     * The model container element columnName. This method gets the exact columnName of the model data in the api. The parser can
     * then traverse the api to find a element with this given columnName. This could also point to the specific element in
     * the api data if the data does not contain multiple values.
     * @return the container columnName for the model in the api
     * @see Endpoint.NodeReader
     * @see Endpoint.EndpointAPIReader
     */
    String container();

    /**
     * Get if the api model contains pagination in terms of multiple api-calls for the whole data set. If true the
     * Endpoint reader should traverse the api for multiple calls to download multiple times. This is done acording to
     * the pagination class.
     * @return if paginated or not
     * @see Pagination
     * @see Endpoint.NodeReader
     * @see Endpoint.EndpointAPIReader
     */
    boolean pagination() default false;
}
