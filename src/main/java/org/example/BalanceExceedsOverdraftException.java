package org.example;

public class BalanceExceedsOverdraftException extends Exception {
    public BalanceExceedsOverdraftException(String message) {
        super(message);
    }
}
