package Models;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by mattias on 1/11/17.
 * <p>
 * The annotation interface for displaying a model in table. If implement this interface on the model it will be visible
 * in a JTable.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //can use in method only.
public @interface TableDisplay {

    /**
     * Gets the preferred column index.
     * @return the index.
     */
    public int order() default 99;

    /**
     * Gets the column name for this value.
     * @return the name.
     */
    public String columnName();
}
