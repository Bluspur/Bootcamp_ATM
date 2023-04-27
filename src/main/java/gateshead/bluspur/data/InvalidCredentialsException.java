package gateshead.bluspur.data;

/**
 * Checked exception thrown when invalid credentials are used to log in.
 * @author craig
 */
public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
