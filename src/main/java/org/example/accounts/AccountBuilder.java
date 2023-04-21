package org.example.accounts;

import java.math.BigDecimal;
import java.util.Optional;

public class AccountBuilder {
    private String name = "";
    private Optional<String> signatory = Optional.empty();
    private BigDecimal openingBalance = BigDecimal.ZERO;
    private AccountType type = null;

    public void setName(String name) throws IllegalArgumentException {
        if (name.isBlank())
            throw new IllegalArgumentException("Name must not be empty.");
        this.name = name;
    }

    public void setSignatory(String signatory) throws IllegalArgumentException {
        if (signatory.isBlank())
            throw new IllegalArgumentException("Signature must not be blank if a signature is required.");
        this.signatory = Optional.of(signatory);
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
