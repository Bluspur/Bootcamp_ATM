package gateshead.bluspur.data;

/**
 * Checked exception thrown when a user is not found in the database.
 * @author craig
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}
