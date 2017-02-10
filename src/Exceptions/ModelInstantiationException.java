package Exceptions;

/**
 * Created by mattias on 2/8/17.
 * <p>
 * Exception generated if the model could not be instantiated or setter methods not of correct type
 */
public class ModelInstantiationException extends Exception {

    /**
     * Constructor for the class
     * @param m the exception message
     */
    public ModelInstantiationException(String m) {
        super(m);
    }
}
