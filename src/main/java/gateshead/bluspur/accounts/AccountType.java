package gateshead.bluspur.accounts;

import java.math.BigDecimal;

public enum AccountType {
    SMALL_BUSINESS,
    COMMUNITY,
    CLIENT;

    public static AccountType parse(String input) throws IllegalArgumentException {
        input = input.toLowerCase();
        return switch (input) {
            case "cl", "client" -> AccountType.CLIENT;
            case "co", "community" -> AccountType.COMMUNITY;
            case "sb", "small business", "business" -> AccountType.SMALL_BUSINESS;
            default -> throw new IllegalArgumentException(input + " is not a valid account type.");
        };
    }

    public BigDecimal getOverdraftLimit() {
        return switch (this) {
            case SMALL_BUSINESS -> new BigDecimal("1000");
            case COMMUNITY -> new BigDecimal("2500");
            case CLIENT -> new BigDecimal("1500");
        };
    }
}
