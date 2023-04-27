package gateshead.bluspur.accounts;

import gateshead.bluspur.users.Username;

import java.math.BigDecimal;

/**
 * Represents an account in the ATM.
 * An account has a name, a balance, and a type.
 * There is also an optional signatory, which is used to restrict access to
 * certain methods.
 * Provides various methods for manipulating the balance.
 * @author craig
 */
public class Account implements java.io.Serializable {
    private final String name;
    private final Username signatory;
    private BigDecimal balance;
    private final AccountType type;

    /**
     * Creates a new account with the given name, signatory, opening balance, and type.
     * @param name The name of the account.
     * @param signatory The signatory of the account.
     * @param openingBalance The opening balance of the account.
     * @param type The type of the account.
     * @throws IllegalArgumentException If the opening balance is negative.
     */
    Account(String name, Username signatory, BigDecimal openingBalance, AccountType type) {
        validateValueIsNonNegative(openingBalance);
        this.name = name;
        this.signatory = signatory;
        this.balance = openingBalance;
        this.type = type;
    }

    /**
     * @return The balance of the account as a BigDecimal.
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * @return The name of the account.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The type of the account.
     */
    public AccountType getType() {
        return type;
    }

    /**
     * Checks if the account requires a signatory.
     * @return True if the account requires a signatory, false otherwise.
     */
    public boolean hasRestrictions() {
        return signatory != null;
    }

    /**
     * Checks if the given username (signature) matches the signatory of the account.
     * @param signatory The username to check.
     * @return True if the given username matches the signatory of the account, false otherwise.
     */
    public boolean verifySignatory(Username signatory) {
        if (this.signatory == null)
            return false;
        return this.signatory.equals(signatory);
    }

    /**
     * Attempts to add the given balance to the account.
     * @param balanceToAdd The balance to add to the account.
     * @throws IllegalArgumentException If the given balance is negative.
     */
    public void addFunds(BigDecimal balanceToAdd) {
        validateValueIsNonNegative(balanceToAdd);

        balance = balance.add(balanceToAdd);
    }

    /**
     * Attempts to subtract the given balance from the account.
     * @param balanceToSubtract The balance to subtract from the account.
     * @throws IllegalArgumentException If the given balance is negative.
     * @throws InsufficientFundsException If the balance after subtraction would be lower than the overdraft limit.
     */
    public void withdrawFunds(BigDecimal balanceToSubtract) throws InsufficientFundsException {
        validateValueIsNonNegative(balanceToSubtract);

        if (balance.subtract(balanceToSubtract).compareTo(BigDecimal.ZERO.subtract(type.getOverdraftLimit())) < 0)
            throw new InsufficientFundsException("Balance must not be lower than overdraft limit.");

        balance = balance.subtract(balanceToSubtract);
    }

    /**
     * Checks if the given balance is non-negative.
     * @param value The balance to check.
     * @throws IllegalArgumentException If the given balance is negative.
     */
    static void validateValueIsNonNegative(BigDecimal value) {
        if (value.signum() == -1)
            throw new IllegalArgumentException("Value must not be negative.");
    }

    /**
     * @return The overdraft limit of the account.
     */
    public BigDecimal getOverdraft() {
        return type.getOverdraftLimit();
    }
}
