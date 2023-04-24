package org.example.terminalApp.input;

import org.example.Command;
import org.example.InvalidCommandException;
import org.example.accounts.Account;
import org.example.accounts.AccountType;
import org.example.terminalApp.TerminalIO;
import org.example.terminalApp.UserSession;
import org.example.users.Password;
import org.example.users.Username;

import java.math.BigDecimal;

public class InputHelper {
    private final TerminalIO io;
    private static final String DEFAULT_MESSAGE_CURRENCY = "Please enter a valid amount";
    private static final String DEFAULT_MESSAGE_USERNAME = "Please enter a valid username";
    private static final String DEFAULT_MESSAGE_PASSWORD = "Please enter a valid password";
    // Currency Regex by Tom Persing at https://regexlib.com/REDetails.aspx?regexp_id=196. Modified to remove dollar sign.
    private final String CURRENCY_REGEX
            = "^([1-9][0-9]{0,2}(,[0-9]{3})*(\\.[0-9]{0,2})?|[1-9][0-9]*(\\.[0-9]{0,2})?|0(\\.[0-9]{0,2})?|(\\.[0-9]{1,2})?)$";

    public InputHelper(TerminalIO config) {
        this.io = config;
    }

    public BigDecimal getPositiveCurrencyAmountFromUser() {
        return getPositiveCurrencyAmountFromUser(DEFAULT_MESSAGE_CURRENCY);
    }

    // Recursive method to get a value from the user that is correctly formatted as currency.
    // Recursion means that we don't need to worry about the user entering invalid input.
    public BigDecimal getPositiveCurrencyAmountFromUser(String message) {
        io.output.print(message + ": £");
        String userInput = io.input.nextLine();
        // Check input matches regex
        if (!userInput.isEmpty() && userInput.matches(CURRENCY_REGEX)) {
            // Remove commas from input
            userInput = userInput.replaceAll(",", "");
            return new BigDecimal(userInput);
        }
        else {
            io.output.println("Error: Invalid input. Please try again.");
            return getPositiveCurrencyAmountFromUser(message);
        }
    }

    public Command getCommandFromUser() throws InvalidCommandException {
        String userInput = io.input.nextLine();
        try {
            return Command.parse(userInput);
        } catch (InvalidCommandException e) {
            throw e;
        }
    }

    public AccountType getAccountTypeFromUser() {
        io.output.print("Client[cl], Community[co], or Small Business [sb]: ");
        String userInput = io.input.nextLine();
        try {
            return AccountType.parse(userInput);
        } catch (IllegalArgumentException e) {
            io.output.println("Error: Invalid input. Please try again.");
            return getAccountTypeFromUser();
        }
    }

    public String getAccountNameFromUser() {
        io.output.print("Please enter a name for the account: ");
        String userInput = io.input.nextLine();
        if (userInput.isEmpty()) {
            io.output.println("Error: Invalid input. Please try again.");
            return getAccountNameFromUser();
        }
        return userInput;
    }

    public boolean getBooleanFromUser() {
        io.output.print("Yes[y] or No[n]: ");
        String userInput = io.input.nextLine();
        boolean result;

        switch (userInput.toLowerCase()) {
            case "y" -> {
                return true;
            }
            case "n" -> {
                return false;
            }
            default -> {
                io.output.println("Error: Invalid input. Please try again.");
                return getBooleanFromUser();
            }
        }
    }

    // Recursive method to get a valid account from the user.
    // Recursion means that we don't need to worry about the user entering invalid input.
    public Account getAccountFromUser(String message, UserSession userSession) {
        io.output.print(message + ": ");
        String userInput = io.input.nextLine();
        try {
            int accountIndex = Integer.parseInt(userInput);
            return userSession.getAccount(accountIndex - 1);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            io.output.println("Error: Invalid input. Please try again.");
            return getAccountFromUser(message, userSession);
        }
    }

    public Username getUsernameFromUser() {
        return getUsernameFromUser(DEFAULT_MESSAGE_USERNAME);
    }

    public Username getUsernameFromUser(String message) {
        io.output.print(message + ": ");
        String userInput = io.input.nextLine();
        try {
            return new Username(userInput);
        } catch (IllegalArgumentException e) {
            io.output.println("Error: Invalid input. Please try again.");
            return getUsernameFromUser(message);
        }
    }

    public Password getPasswordFromUser() {
        return getPasswordFromUser(DEFAULT_MESSAGE_PASSWORD);
    }

    public Password getPasswordFromUser(String message) {
        io.output.print(message + ": ");
        String userInput = io.input.nextLine();
        try {
            return new Password(userInput);
        } catch (IllegalArgumentException e) {
            io.output.println("Error: Invalid input. Please try again.");
            return getPasswordFromUser(message);
        }
    }
}
