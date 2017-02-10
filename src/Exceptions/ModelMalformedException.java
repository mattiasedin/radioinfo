package Exceptions;

/**
 * Created by mattias on 2/10/17.
 * <p>
 * Exception called if the model does not match the given parsing node.
 */
public class ModelMalformedException extends Exception {

    /**
     * Constructor for the class
     * @param m the exception message
     */
    public ModelMalformedException(String m) {
        super(m);
    }
}
