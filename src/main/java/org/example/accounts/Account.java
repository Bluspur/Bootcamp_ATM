package org.example.accounts;

import java.math.BigDecimal;
import java.util.Optional;

public class Account {
    private final String name;
    private final Optional<String> signatory;
    private BigDecimal balance;
    private final AccountType type;

    Account(String name, Optional<String> signatory, BigDecimal openingBalance, AccountType type) {
        validateValueIsNonNegative(openingBalance);
        this.name = name;
        this.signatory = signatory;
        this.balance = openingBalance;
        this.type = type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public AccountType getType() {
        return type;
    }

    public boolean hasRestrictions() {
        return signatory.isPresent();
    }

    public boolean verifySignatory(String signatory) {
        if (this.signatory.isEmpty())
            return false;
        return this.signatory.equals(Optional.of(signatory));
    }

    public void addFunds(BigDecimal balanceToAdd) {
        validateValueIsNonNegative(balanceToAdd);

        balance = balance.add(balanceToAdd);
    }

    public void subtractFunds(BigDecimal balanceToSubtract) throws BalanceExceedsOverdraftException {
        validateValueIsNonNegative(balanceToSubtract);

        if (balance.subtract(balanceToSubtract).compareTo(BigDecimal.ZERO.subtract(type.getOverdraftLimit())) < 0)
            throw new BalanceExceedsOverdraftException("Account balance must not exceed overdraft limit");

        balance = balance.subtract(balanceToSubtract);
    }

    static void validateValueIsNonNegative(BigDecimal value) {
        if (value.signum() == -1)
            throw new IllegalArgumentException("Value must not be negative.");
    }
}
