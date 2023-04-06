package org.example;

import java.math.BigDecimal;

public class CommunityAccount extends Account {
    public CommunityAccount(BigDecimal openingBalance) {
        super(openingBalance, AccountType.COMMUNITY.getOverdraftLimit());
    }
}
