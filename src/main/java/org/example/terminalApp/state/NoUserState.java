package org.example.terminalApp.state;

import org.example.Command;
import org.example.data.InvalidCredentialsException;
import org.example.data.UserAlreadyExistsException;
import org.example.data.UserNotFoundException;
import org.example.terminalApp.TerminalSession;
import org.example.users.Password;
import org.example.users.User;
import org.example.users.Username;

import java.util.EnumSet;

public class NoUserState extends State{
    public NoUserState (TerminalSession terminalSession) {
        super(terminalSession);
    }

    @Override
    public void printContextualInformation() {
        context.output.println("No Current User. Login[l] or Register[r] a new user to continue.");
    }

    private final EnumSet<Command> validCommands
            = EnumSet.of(Command.Login, Command.Register, Command.Help, Command.Quit);

    @Override
    public void handleCommand(Command command) {
        switch (command) {
            case Login -> login();
            case Register -> register();
            case Help -> context.printHelp(validCommands);
            case Quit -> context.stop();
            default -> context.output.println("Error: " + command + " is not possible before logging in.");
        }
    }

    private void login() {
        Username username;
        Password password;
        User user;

        context.output.println("Existing User Login");
        context.output.print("Enter your Username: ");
        String rawUsername = context.input.nextLine();
        try {
            username = new Username(rawUsername);
        } catch (IllegalArgumentException e) {
            context.output.println("Invalid: " + e.getMessage());
            return;
        }

        context.output.print("Enter a Password: ");
        String rawPassword = context.input.nextLine();

        try {
            password = new Password(rawPassword);
        } catch (IllegalArgumentException e) {
            context.output.println("Invalid: " + e.getMessage());
            return;
        }

        try {
            user = context.database.login(username, password);
            context.userSession.currentUser = user;
            context.userSession.accounts = context.database.getUserAccounts(context.userSession.currentUser);
            context.output.println("Login Successful");
            context.changeState(new LoggedInState(context));
        } catch (UserNotFoundException e) {
            context.output.println("Error: " + e.getMessage());
        } catch (InvalidCredentialsException e) {
            context.output.println("Login Failed: " + e.getMessage());
        }
    }

    private void register() {
        Username username;
        Password password;

        context.output.println("New User Registration");
        context.output.print("Enter a Username: ");
        String rawUsername = context.input.nextLine();
        try {
            username = new Username(rawUsername);
        } catch (IllegalArgumentException e) {
            context.output.println("Invalid: " + e.getMessage());
            return;
        }

        context.output.print("Enter a Password: ");
        String rawPassword = context.input.nextLine();

        try {
            password = new Password(rawPassword);
        } catch (IllegalArgumentException e) {
            context.output.println("Invalid: " + e.getMessage());
            return;
        }

        try {
            context.userSession.currentUser = context.database.add(username, password);
            context.userSession.accounts = context.database.getUserAccounts(context.userSession.currentUser);
            context.output.println("User Successfully Registered!");
            context.changeState(new LoggedInState(context));
        } catch (UserAlreadyExistsException e) {
            context.output.println("User Registration Failed: " + e.getMessage());
        }
    }
}
