package gateshead.bluspur.data;

/**
 * Checked exception thrown when attempting to create a user with
 * a username that already exists.
 * @author craig
 */
public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
