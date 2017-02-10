package Exceptions;

/**
 * Created by mattias on 2016-01-07.
 * <p>
 * Exception generated if the specific setter model value could not be inserted.
 */
public class ModelParseException extends Exception {
    /**
     * Constructor for the class
     * @param m the exception message
     */
    public ModelParseException(String m) {
        super(m);
    }
}
