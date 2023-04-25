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

public class TerminalSession {
    private final TerminalIO io;
    public final InputHelper inputHelper;
    public final Database database;

    private State state = new LoggedOutState(this);
    public UserSession userSession = null;
    private boolean running = false;

    public TerminalSession(TerminalIO terminalIO, Database database) {
        this.io = terminalIO;
        this.inputHelper = new InputHelper(io);
        this.database = database;
    }

    public void run() {
        mainLoop();
    }

    private void printIntroMessage() {
        io.output.println("Welcome to Craig's ATM.");
        io.output.println("Type \"help\" at any time to see a list of available commands.");
        printLine();
    }

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

    public void printHelp(EnumSet<Command> validCommands) {
        for (Command command : validCommands) {
            io.output.print(Command.getHelp(command));
        }
    }

    public void printMessage(String message) {
        io.output.print(message + "\n");
    }

    public void changeState(State state) {
        this.state = state;
    }

    public LoginOutcome login(Username username, Password password) {
        try {
            User user = database.login(username, password);
            List<Account> userAccounts = database.getAccountsForUser(user);
            userSession = new UserSession(user, userAccounts);
            return LoginOutcome.SUCCESS;
        } catch (UserNotFoundException e) {
            return LoginOutcome.USER_NOT_FOUND;
        } catch (InvalidCredentialsException e) {
            return LoginOutcome.INCORRECT_PASSWORD;
        }
    }

    public void logout() {
        userSession = null;
        changeState(new LoggedOutState(this));
    }

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
        printLine();
    }

    public void printAccountInfo() {
        if (userSession.getAccounts().size() == 0) {
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
            serializeDatabase();
            userSession.addAccount(acc);
        } catch (InvalidBuilderException e) {
            printMessage("Error: " + e.getMessage());
        }
    }

    public Account getAccountFromCurrentUser(String message) {
            return inputHelper.getAccountFromUser(message, userSession);
    }

    public void serializeDatabase() {
        try {
            database.save();
        } catch (IOException e) {
            printMessage("Error: " + e.getMessage());
        }
    }
}