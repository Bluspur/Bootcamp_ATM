package org.example;

import java.math.BigDecimal;

public abstract class Account {
    private BigDecimal balance;
    private final BigDecimal overdraftLimit;

    public Account(BigDecimal openingBalance, BigDecimal overdraftLimit) {
        this.balance = openingBalance;
        this.overdraftLimit = overdraftLimit;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void addFunds(BigDecimal balanceToAdd) {
        validateValueIsNonNegative(balanceToAdd);

        balance = balance.add(balanceToAdd);
    }

    public void subtractFunds(BigDecimal balanceToSubtract) throws BalanceExceedsOverdraftException {
        validateValueIsNonNegative(balanceToSubtract);

        if (balance.subtract(balanceToSubtract).compareTo(BigDecimal.ZERO.subtract(overdraftLimit)) < 0)
            throw new BalanceExceedsOverdraftException("Account balance must not exceed overdraft limit");

        balance = balance.subtract(balanceToSubtract);
    }

    private void validateValueIsNonNegative(BigDecimal value) {
        if (value.signum() == -1)
            throw new IllegalArgumentException("Value must not be negative.");
    }
}
