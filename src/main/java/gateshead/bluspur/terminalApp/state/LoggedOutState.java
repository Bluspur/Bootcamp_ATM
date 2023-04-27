package gateshead.bluspur.terminalApp.state;

import gateshead.bluspur.users.Password;
import gateshead.bluspur.users.Username;
import gateshead.bluspur.Command;
import gateshead.bluspur.terminalApp.TerminalSession;

import java.util.EnumSet;

/**
 * State representing the terminal session when no user is logged in.
 * @author craig
 */
public class LoggedOutState extends State{
    private final EnumSet<Command> validCommands = EnumSet.of(
            Command.Login,
            Command.Register,
            Command.Help,
            Command.Quit
    );

    public LoggedOutState(TerminalSession terminalSession) {
        super(terminalSession);
    }

    @Override
    public void printContextualInformation() {
        context.printMessage("No Current User. Login[l] or Register[r] a new user to continue.");
    }

    @Override
    public void handleCommand(Command command) {
        switch (command) {
            case Login -> login();
            case Register -> register();
            case Help -> context.printHelp(validCommands);
            case Quit -> context.stop();
            default -> context.printMessage("Error: " + command
                    + " is not possible before logging in.");
        }
    }

    private void login() {
        context.printMessage("Login");
        Username username = context.inputHelper.getUsernameFromUser("Enter a Username");
        Password password = context.inputHelper.getPasswordFromUser("Enter a Password");

        switch (context.logIn(username, password)) {
            case SUCCESS -> {
                context.printMessage("Login Successful");
                context.changeState(new LoggedInState(context));
            }
            case USER_NOT_FOUND -> context.printMessage("Error: User not found.");
            case INCORRECT_PASSWORD -> context.printMessage("Login Failed: Incorrect Password.");
        }
    }

    private void register() {
        context.printMessage("Register");
        Username username = context.inputHelper.getUsernameFromUser("Enter a Username");
        Password password = context.inputHelper.getPasswordFromUser("Enter a Password");

        switch (context.register(username, password)) {
            case SUCCESS -> {
                context.printMessage("Registration Successful");
                context.serializeDatabase();
                context.changeState(new LoggedInState(context));
            }
            case USER_ALREADY_EXISTS -> context.printMessage("Registration Failed: User already exists.");
        }
    }
}
