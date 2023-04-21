package org.example.accounts;

public class BalanceExceedsOverdraftException extends Exception {
    public BalanceExceedsOverdraftException(String message) {
        super(message);
    }
}
