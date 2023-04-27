package gateshead.bluspur.accounts;

import java.math.BigDecimal;

/**
 * An enum representing the type of account.
 * Each type has a different overdraft limit.
 * This enum also provides a method to parse a string into an account type.
 * @author craig
 */
public enum AccountType {
    SMALL_BUSINESS,
    COMMUNITY,
    CLIENT;

    /**
     * Parses a string into an account type.
     * @param input The string to parse.
     * @return The parsed account type.
     * @throws IllegalArgumentException If the string is not a valid account type.
     */
    public static AccountType parse(String input) throws IllegalArgumentException {
        input = input.toLowerCase();
        return switch (input) {
            case "cl", "client" -> AccountType.CLIENT;
            case "co", "community" -> AccountType.COMMUNITY;
            case "sb", "small business", "business" -> AccountType.SMALL_BUSINESS;
            default -> throw new IllegalArgumentException(input + " is not a valid account type.");
        };
    }

    /**
     * Gets the overdraft limit for this account type.
     * @return The overdraft limit for this account type.
     */
    public BigDecimal getOverdraftLimit() {
        return switch (this) {
            case SMALL_BUSINESS -> new BigDecimal("1000");
            case COMMUNITY -> new BigDecimal("2500");
            case CLIENT -> new BigDecimal("1500");
        };
    }
}
