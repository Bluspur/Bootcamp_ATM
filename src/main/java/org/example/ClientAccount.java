package org.example;

import java.math.BigDecimal;

public class ClientAccount extends Account {
    public ClientAccount(BigDecimal openingBalance) {
        super(openingBalance, AccountType.CLIENT.getOverdraftLimit());
    }
}
