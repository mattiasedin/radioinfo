package Models;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by mattias on 1/18/17.
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface ApiModel {
    public String container();

    public boolean pagination() default false;
}
