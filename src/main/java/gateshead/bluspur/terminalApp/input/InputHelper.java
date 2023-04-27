package gateshead.bluspur.terminalApp.input;

import gateshead.bluspur.Command;
import gateshead.bluspur.InvalidCommandException;
import gateshead.bluspur.accounts.Account;
import gateshead.bluspur.accounts.AccountType;
import gateshead.bluspur.terminalApp.TerminalIO;
import gateshead.bluspur.terminalApp.UserSession;
import gateshead.bluspur.users.Password;
import gateshead.bluspur.users.Username;

import java.math.BigDecimal;

/**
 * A helper class for getting input from the user.
 * Helps to ensure that the input is valid and of the correct type.
 * Most methods are recursive, so that if the user enters invalid input, they are prompted
 * to enter it again. This is to guarantee that the input is valid.
 * @author craig
 */
public class InputHelper {
    private final TerminalIO io;
    private static final String DEFAULT_MESSAGE_CURRENCY = "Please enter a valid amount";

    /**
     * Regular expression that matches on a valid currency amount, including commas.
     * By Tom Persing at <a href="https://regexlib.com/REDetails.aspx?regexp_id=196">RegexLib</a>.
     * Modified to remove dollar sign.
     */
    private static final String CURRENCY_REGEX
            = "^([1-9][0-9]{0,2}(,[0-9]{3})*(\\.[0-9]{0,2})?|[1-9][0-9]*(\\.[0-9]{0,2})?|0(\\.[0-9]{0,2})?|(\\.[0-9]{1,2})?)$";

    public InputHelper(TerminalIO config) {
        this.io = config;
    }

    /**
     * Polls the user for a valid amount of currency, in this case in pounds sterling.
     * Provides a default message to the user.
     * @see #getPositiveCurrencyAmountFromUser(String)
     * @return
     */
    public BigDecimal getPositiveCurrencyAmountFromUser() {
        return getPositiveCurrencyAmountFromUser(DEFAULT_MESSAGE_CURRENCY);
    }

    /**
        * Polls the user for a valid amount of currency, in this case in pounds sterling.
        * @param message The message to display to the user when asking for input.
        * @return The amount of currency entered by the user as a BigDecimal.
     */
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

    /**
     * Polls the user for a valid Command.
     * @see Command
     * @return The Command represented by the user's input.
     * @throws InvalidCommandException If the user's input does not match a valid Command.
     */
    public Command getCommandFromUser() throws InvalidCommandException {
        String userInput = io.input.nextLine();
        return Command.parse(userInput);
    }

    /**
     * Polls the user for a valid AccountType.
     * @see AccountType
     * @return The AccountType represented by the user's input.
     */
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

    /**
     * Polls the user for an account name. Only check is that the input is not empty.
     * @return The account name entered by the user.
     */
    public String getAccountNameFromUser() {
        io.output.print("Please enter a name for the account: ");
        String userInput = io.input.nextLine();
        if (userInput.isEmpty()) {
            io.output.println("Error: Invalid input. Please try again.");
            return getAccountNameFromUser();
        }
        return userInput;
    }

    /**
     * Polls the user for a valid boolean value.
     * @return The boolean value represented by the user's input.
     */
    public boolean getBooleanFromUser() {
        io.output.print("Yes[y] or No[n]: ");
        String userInput = io.input.nextLine();

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

    /**
     * Polls the user for a valid account number.
     * @see Account
     * @param message The message to display to the user when asking for input.
     * @param userSession The user session to check the account number against.
     * @return The Account object at the index entered by the user.
     */
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

    /**
     * Polls the user for a valid username.
     * @see Username
     * @param message The message to display to the user when asking for input.
     * @return The Username object entered by the user.
     */
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

    /**
     * Polls the user for a valid password.
     * @see Password
     * @param message The message to display to the user when asking for input.
     * @return The Password object entered by the user.
     */
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
