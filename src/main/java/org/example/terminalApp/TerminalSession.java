package org.example.terminalApp;

import org.example.Command;
import org.example.InvalidCommandException;
import org.example.accounts.Account;
import org.example.data.Database;
import org.example.terminalApp.state.*;

import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.EnumSet;
import java.util.Scanner;

public class TerminalSession {
    public final Database database;
    public final UserSession userSession;
    public final Scanner input;
    public final PrintStream output;
    public State state;
    private boolean running = false;

    public TerminalSession(Database database, InputStream inputStream, PrintStream outputStream) {
        this.database = database;
        this.userSession = new UserSession();
        this.input = new Scanner(inputStream);
        this.output = outputStream;
        this.state = new NoUserState(this);
    }

    public void run() {
        printIntroMessage();
        running = true;
        mainLoop();
    }

    private void mainLoop() {
        while (running) {
            state.printContextualInformation();
            printUserPrompt();
            String userInput = input.nextLine();
            try {
                Command userCommand = Command.parse(userInput);
                state.handleCommand(userCommand);
            } catch (InvalidCommandException e) {
                output.println("Error: " + e.getMessage());
            }
            printLine();
        }
        printStopMessage();
    }

    public void stop() {
        running = false;
    }

    private void printIntroMessage() {
        output.println("Welcome to Craig's ATM.");
        output.println("Type \"help\" at any time to see a list of available commands.");
        printLine();
    }

    private void printStopMessage() {
        output.println("Exiting Application.");
    }

    private void printLine() {
        output.println("-------------------------------------------------------------"
                + "---------------------------------------------------------------------");
    }

    private void printUserPrompt() {
        output.print(userSession.getCurrentUser() + " > ");
    }

    public void printHelp(EnumSet<Command> validCommands) {
        for (Command command : validCommands) {
            output.print(Command.getHelp(command));
        }
    }

    public void changeState(State state) {
        this.state = state;
    }

    public void logout() {
        userSession.currentUser = null;
        userSession.accounts = null;
        changeState(new NoUserState(this));
    }

    public void printAccountsTable() {
        output.printf(
                "%-30s| %30s | %30s | %30s |%n",
                "Number",
                "Name",
                "Type",
                "Balance");
        printLine();
        for (int i = 0; i < userSession.accounts.size(); i++) {
            output.printf(
                    "%-30d| %30s | %30s | £%,29.2f |%n",
                    i + 1,
                    userSession.accounts.get(i).getName(),
                    userSession.accounts.get(i).getType(),
                    userSession.accounts.get(i).getBalance()
            );
        }
        printLine();
    }

    public void printAccountDetailed(Account account) {
        output.printf("%-15s %20s%n","Name: ", account.getName());
        output.printf("%-15s %20s%n","Type: ", account.getType());
        output.printf("%-15s %20s%n","Restricted?: ", account.hasRestrictions() ? "Yes" : "No");
        output.printf("%-15s %,20.2f%n","Balance: £", account.getBalance());
        printLine();
    }

    public BigDecimal pollUserForAmount() {
        output.print("Enter amount: ");
        String userInput = input.nextLine();
        try {
            return new BigDecimal(userInput, new MathContext(2, RoundingMode.HALF_EVEN));
        } catch (NumberFormatException e) {
            output.println("Error: Invalid amount.");
            return null;
        }
    }

    public Account selectAccount() {
        output.print("Select an account: ");
        String accountNumber = input.nextLine();
        try {
            int accountNumberInt = Integer.parseInt(accountNumber);
            return userSession.accounts.get(accountNumberInt - 1);
        } catch (NumberFormatException e) {
            output.println("Error: Invalid account number.");
            return null;
        } catch (IndexOutOfBoundsException e) {
            output.println("Error: No account with that number.");
            return null;
        }
    }
}