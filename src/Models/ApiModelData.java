package Models;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by mattias on 1/18/17.
 * <p>
 * Annotation interface for each data element in the model. This interface describes how the data is represented in the
 * API. The types represented in the api will be parsed differently. This interface is used for each setter method in
 * the model.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //can use in method only.
public @interface ApiModelData {

    /**
     * Gets how the data is represented in the API.
     * @return the type.
     */
    public CONTENT_TYPES type();

    /**
     * The api element or attribute columnName in the model
     * @return the columnName of the value
     */
    public String name();

    /**
     * The nested object type, which class to instantiate and parse with.
     * @return the typed class.
     */
    public Class<?> nestedObjectType() default Object.class;

    /**
     * The different xml types.
     */
    public enum CONTENT_TYPES {
        attribute, // the the value is of xml attribute value
        innercontent, // value is the content of the element
        nestedObject // the element is of a nested object and should be parsed further
    }
}
