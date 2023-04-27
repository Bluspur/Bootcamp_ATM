package gateshead.bluspur.terminalApp;

import gateshead.bluspur.accounts.*;
import gateshead.bluspur.data.*;
import gateshead.bluspur.terminalApp.input.*;
import gateshead.bluspur.terminalApp.state.*;
import gateshead.bluspur.users.*;
import gateshead.bluspur.Command;
import gateshead.bluspur.InvalidCommandException;

import java.io.IOException;
import java.util.EnumSet;
import java.util.List;

/**
 * Class that represents a session of the terminal application.
 * This class is responsible for the main loop of the application.
 * It also holds the current log-in state and a reference to the current user session.
 * @author craig
 */
public class TerminalSession {
    private final TerminalIO io;
    public final InputHelper inputHelper;
    public final Database database;

    private State state = new LoggedOutState(this);
    /**
     * The current user session.
     */
    public UserSession userSession = null;
    private boolean running = false;

    /**
     * Creates a new terminal session.
     * @param terminalIO The terminal IO object.
     * @param database The database object.
     */
    public TerminalSession(TerminalIO terminalIO, Database database) {
        this.io = terminalIO;
        this.inputHelper = new InputHelper(io);
        this.database = database;
    }

    /**
     * Begins the main loop of the application.
     */
    public void run() {
        mainLoop();
    }

    private void printIntroMessage() {
        io.output.println("Welcome to Craig's ATM.");
        io.output.println("Type \"help\" at any time to see a list of available commands.");
        printLine();
    }

    /**
     * Manages the main loop of the application.
     * Handles collecting user input and passing it to the current state.
     */
    private void mainLoop() {
        printIntroMessage();

        running = true;

        while (running) {
            state.printContextualInformation();
            printUserPrompt();
            try {
                Command userCommand = inputHelper.getCommandFromUser();
                state.handleCommand(userCommand);
            } catch (InvalidCommandException e) {
                printMessage("Error: " + e.getMessage());
            } finally {
                printLine();
            }
        }

        printStopMessage();
    }

    /**
     * Stops the main loop of the application.
     */
    public void stop() {
        running = false;
    }

    private void printStopMessage() {
        io.output.println("Exiting Application.");
    }

    private void printLine() {
        io.output.println("-------------------------------------------------------------"
                + "---------------------------------------------------------------------");
    }

    private void printUserPrompt() {
        if (userSession == null) {
            io.output.print("No User > ");
        } else {
            io.output.print(userSession.getUser().getUsername() + " > ");
        }
    }

    /**
     * Prints help messages for the given commands.
     * @param validCommands The EnumSet of commands to print the help message for.
     */
    public void printHelp(EnumSet<Command> validCommands) {
        for (Command command : validCommands) {
            io.output.print(Command.getHelp(command));
        }
    }

    /**
     * Convenience method for allowing State objects to print to the terminal without
     * requiring access to the TerminalIO.
     * @param message The message to print.
     */
    public void printMessage(String message) {
        io.output.print(message + "\n");
    }

    /**
     * Method that changes the current state of the application.
     * @param state The new state.
     */
    public void changeState(State state) {
        this.state = state;
    }

    /**
     * Method to attempt to log in a user from the database.
     * If the user is found and the password is correct, the user is logged in and the
     * user session is set.
     * @param username The username of the user to log in.
     * @param password The password of the user to log in.
     * @return Enum representing the outcome of the login attempt.
     */
    public LoginOutcome logIn(Username username, Password password) {
        try {
            User user = database.logIn(username, password);
            List<Account> userAccounts = database.getAccountsForUser(user);
            userSession = new UserSession(user, userAccounts);
            return LoginOutcome.SUCCESS;
        } catch (UserNotFoundException e) {
            return LoginOutcome.USER_NOT_FOUND;
        } catch (InvalidCredentialsException e) {
            return LoginOutcome.INCORRECT_PASSWORD;
        }
    }

    /**
     * Reset the userSession and change the state to LoggedOutState.
     */
    public void logout() {
        userSession = null;
        changeState(new LoggedOutState(this));
    }

    /**
     * Method to attempt to register a new user in the database.
     * If the user is successfully registered, the user is logged in and the user session
     * is set.
     * @param username The username of the user to register.
     * @param password The password of the user to register.
     * @return Enum representing the outcome of the registration attempt.
     */
    public RegisterOutcome register(Username username, Password password) {
        try {
            User user = database.register(username, password);
            List<Account> userAccounts = database.getAccountsForUser(user);
            userSession = new UserSession(user, userAccounts);
            return RegisterOutcome.SUCCESS;
        } catch (UserAlreadyExistsException e) {
            return RegisterOutcome.USER_ALREADY_EXISTS;
        }
    }

    /**
     * Helper method to format and print a table of the current user's accounts.
     */
    public void printAccountsTable() {
        io.output.printf(
                "%-30s| %30s | %30s | %30s |%n",
                "Number",
                "Name",
                "Type",
                "Balance");
        printLine();
        for (int i = 0; i < userSession.getAccounts().size(); i++) {
            io.output.printf(
                    "%-30d| %30s | %30s | £%,29.2f |%n",
                    i + 1,
                    userSession.getAccounts().get(i).getName(),
                    userSession.getAccounts().get(i).getType(),
                    userSession.getAccounts().get(i).getBalance()
            );
        }
    }

    /**
     * Method that prints the details of a single account.
     * If the user has no accounts, a message informing them is printed.
     */
    public void printAccountInfo() {
        if (userSession.getAccountsCount() < 1) {
            io.output.println("You have no accounts.");
        } else {
            Account account = inputHelper
                    .getAccountFromUser("Enter an Account Number to view", userSession);
            printFormattedAccount(account);
        }
    }

    private void printFormattedAccount(Account account) {
        io.output.printf("%-15s %20s%n","Name: ", account.getName());
        io.output.printf("%-15s %20s%n","Type: ", account.getType());
        io.output.printf("%-15s %20s%n","Restricted?: ", account.hasRestrictions() ? "Yes" : "No");
        io.output.printf("%-15s %,20.2f%n","Overdraft: £", account.getOverdraft());
        io.output.printf("%-15s %,20.2f%n","Balance: £", account.getBalance());
        printLine();
    }

    /**
     * Method that manages the process of creating a new account.
     * The user is prompted for the type of account, the name of the account, and the
     * opening balance.
     * If the account requires a signatory, the user is prompted for a username.
     * If the account is successfully created, it is added to the user's accounts and
     * the database is serialized.
     * If the account cannot be created, prints an error.
     * @see AccountBuilder
     * @see Account
     */
    public void openNewAccount() {
        var accBuilder = new AccountBuilder();

        printMessage("Which type of account?");
        accBuilder.setType(inputHelper.getAccountTypeFromUser());
        accBuilder.setName(inputHelper.getAccountNameFromUser());
        accBuilder.setOpeningBalance(inputHelper.getPositiveCurrencyAmountFromUser("Opening Balance: "));
        printMessage("Does the account require a signatory?");
        if (inputHelper.getBooleanFromUser()) {
            Username signatory = inputHelper.getUsernameFromUser("Enter signature");
            accBuilder.setSignatory(signatory);
        }
        try {
            Account acc = accBuilder.build();
            userSession.addAccount(acc);
            serializeDatabase();
        } catch (InvalidBuilderException e) {
            printMessage("Error: " + e.getMessage());
        }
    }

    /**
     * Convenience method for state objects to be able to get an account from the user.
     * @param message The message to prompt the user with.
     * @return The account selected by the user.
     */
    public Account getAccountFromCurrentUser(String message) {
            return inputHelper.getAccountFromUser(message, userSession);
    }

    /**
     * Method that tries to save the database to file.
     * If the database cannot be saved, prints an error.
     * @see Database
     */
    public void serializeDatabase() {
        try {
            database.save();
        } catch (IOException e) {
            printMessage("Error: " + e.getMessage());
        }
    }
}