package gateshead.bluspur;

/**
 * An exception thrown when an invalid command is entered.
 * @author craig
 */
public class InvalidCommandException extends Exception {
    public InvalidCommandException(String message) {
        super(message);
    }
}
