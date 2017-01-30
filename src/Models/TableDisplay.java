package Models;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by mattias on 1/11/17.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //can use in method only.
public @interface TableDisplay {

    public boolean visible() default true;

    public int order() default -1;

    public String name() default "Unset";

}
