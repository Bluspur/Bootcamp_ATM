package gateshead.bluspur.accounts;

import gateshead.bluspur.users.Username;

import java.math.BigDecimal;

public class AccountBuilder {
    private String name = "";
    private Username signatory = null;
    private BigDecimal openingBalance = BigDecimal.ZERO;
    private AccountType type = null;

    public void setName(String name) throws IllegalArgumentException {
        if (name.isBlank())
            throw new IllegalArgumentException("Name must not be empty.");
        this.name = name;
    }

    public void setSignatory(Username signatory) throws IllegalArgumentException {
        this.signatory = signatory;
    }

    public void setOpeningBalance(BigDecimal openingBalance) throws IllegalArgumentException {
        Account.validateValueIsNonNegative(openingBalance);
        this.openingBalance = openingBalance;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

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
