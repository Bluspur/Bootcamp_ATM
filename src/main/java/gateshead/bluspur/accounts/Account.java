package gateshead.bluspur.accounts;

import gateshead.bluspur.users.Username;

import java.math.BigDecimal;

public class Account implements java.io.Serializable {
    private final String name;
    private final Username signatory;
    private BigDecimal balance;
    private final AccountType type;

    Account(String name, Username signatory, BigDecimal openingBalance, AccountType type) {
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
        return signatory != null;
    }

    public boolean verifySignatory(Username signatory) {
        if (this.signatory == null)
            return false;
        return this.signatory.equals(signatory);
    }

    public void addFunds(BigDecimal balanceToAdd) {
        validateValueIsNonNegative(balanceToAdd);

        balance = balance.add(balanceToAdd);
    }

    public void withdrawFunds(BigDecimal balanceToSubtract) throws InsufficientFundsException {
        validateValueIsNonNegative(balanceToSubtract);

        if (balance.subtract(balanceToSubtract).compareTo(BigDecimal.ZERO.subtract(type.getOverdraftLimit())) < 0)
            throw new InsufficientFundsException("Balance must not be lower than overdraft limit.");

        balance = balance.subtract(balanceToSubtract);
    }

    static void validateValueIsNonNegative(BigDecimal value) {
        if (value.signum() == -1)
            throw new IllegalArgumentException("Value must not be negative.");
    }

    public BigDecimal getOverdraft() {
        return type.getOverdraftLimit();
    }
}
