package gateshead.bluspur.accounts;

import gateshead.bluspur.users.Username;

import java.math.BigDecimal;

/**
 * A builder for {@link Account} objects.
 * Helps to ensure that all required fields are set while allowing for optional fields.
 * Also, helpful for when we need to poll the user for information on creating an account.
 * @author craig
 */
public class AccountBuilder {
    private String name = "";
    private Username signatory = null;
    private BigDecimal openingBalance = BigDecimal.ZERO;
    private AccountType type = null;

    /**
     * Sets the name of the account.
     * @param name The name of the account.
     * @throws IllegalArgumentException If the name is blank.
     */
    public void setName(String name) throws IllegalArgumentException {
        if (name.isBlank())
            throw new IllegalArgumentException("Name must not be empty.");
        this.name = name;
    }

    /**
     * Sets the signatory of the account.
     * @param signatory The signatory of the account.
     * @throws IllegalArgumentException If the signatory is null.
     */
    public void setSignatory(Username signatory) throws IllegalArgumentException {
        this.signatory = signatory;
    }

    /**
     * Sets the opening balance of the account.
     * @param openingBalance The opening balance of the account.
     * @throws IllegalArgumentException If the opening balance is negative.
     */
    public void setOpeningBalance(BigDecimal openingBalance) throws IllegalArgumentException {
        Account.validateValueIsNonNegative(openingBalance);
        this.openingBalance = openingBalance;
    }

    /**
     * Sets the type of the account.
     * @param type The type of the account.
     * @throws IllegalArgumentException If the type is null.
     */
    public void setType(AccountType type) {
        this.type = type;
    }

    /**
     * Builds the account.
     * @return The built account.
     * @throws InvalidBuilderException If the builder is in an invalid state.
     */
    public Account build() throws InvalidBuilderException {
        if (name.isBlank())
            throw new InvalidBuilderException("New Accounts must have a name.");
        if (type == null)
            throw new InvalidBuilderException("New Accounts must have a type.");

        return new Account(
                this.name,
                this.signatory,
                this.openingBalance,
                this.type
        );
    }
}
