package gateshead.bluspur.accounts;

/**
 * Checked exception thrown when an account does not have sufficient
 * funds to perform an operation.
 * @author craig
 */
public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
