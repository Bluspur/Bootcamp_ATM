package org.example;

import java.math.BigDecimal;

public class SmallBusinessAccount extends Account {
    public SmallBusinessAccount(BigDecimal openingBalance) {
        super(openingBalance, AccountType.SMALL_BUSINESS.getOverdraftLimit());
    }
}
