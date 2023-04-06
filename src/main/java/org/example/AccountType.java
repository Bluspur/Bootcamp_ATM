package org.example;

import java.math.BigDecimal;

public enum AccountType {
    SMALL_BUSINESS,
    COMMUNITY,
    CLIENT;

    public BigDecimal getOverdraftLimit() {
        return switch (this) {
            case SMALL_BUSINESS -> new BigDecimal("1000");
            case COMMUNITY -> new BigDecimal("2500");
            case CLIENT -> new BigDecimal("1500");
        };
    }
}
