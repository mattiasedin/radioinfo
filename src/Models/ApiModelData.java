package Models;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by mattias on 1/18/17.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiModelData {
    public CONTENT_TYPES type();

    public String name();

    public Class<?> nestedObjectType() default Object.class;

    public enum CONTENT_TYPES {
        attribute,
        innercontent,
        nestedattribute,
        nestedObject
    }
}
