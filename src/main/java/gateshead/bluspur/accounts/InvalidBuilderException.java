package gateshead.bluspur.accounts;

/**
 * Checked exception thrown when an invalid builder is used to build an account.
 * @author craig
 */
public class InvalidBuilderException extends Exception {
    public InvalidBuilderException(String message) {
        super(message);
    }
}
