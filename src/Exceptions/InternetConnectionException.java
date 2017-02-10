package Exceptions;

/**
 * Created by mattias on 2/9/17.
 * <p>
 * Exception called when internet connection is not working
 */
public class InternetConnectionException extends Exception {

    /**
     * Constructor for the class
     * @param m the exception message
     */
    public InternetConnectionException(String m) {
        super(m);
    }
}
