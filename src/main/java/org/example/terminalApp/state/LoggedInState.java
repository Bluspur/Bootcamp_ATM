package org.example.terminalApp.state;

import org.example.Command;
import org.example.accounts.Account;
import org.example.accounts.AccountBuilder;
import org.example.accounts.AccountType;
import org.example.accounts.InvalidBuilderException;
import org.example.terminalApp.TerminalSession;

import java.math.BigDecimal;
import java.util.EnumSet;

public class LoggedInState extends State {
    public LoggedInState(TerminalSession session) {
        super(session);
    }

    @Override
    public void printContextualInformation() {
        if (context.userSession.accounts.isEmpty())
            context.output.println("You have no open accounts. Open[o] an account to continue.");
        else
            context.printAccountsTable();
    }

    private final EnumSet<Command> validCommands
            = EnumSet.of(Command.Open, Command.Logout, Command.View, Command.Help, Command.Quit);

    @Override
    public void handleCommand(Command command) {
        switch (command) {
            case Open -> openAccount();
            case Login, Register -> context.output.println("Error: User already logged in.");
            case Logout -> context.logout();
            case View -> viewAccount();
            case Help -> context.printHelp(validCommands);
            case Quit -> context.stop();
        }
    }

    private void viewAccount() {
        Account account = context.selectAccount();
        if (account == null) {
            context.output.println("Error: No account selected.");
            return;
        }
        context.state = new ViewingDetailedState(context, account);
    }

    private void openAccount() {
        var accBuilder = new AccountBuilder();

        context.output.println("Which type of account do you want to open?");
        context.output.print("Client[cl], Community[co], or Small Business [sb]: ");
        String input = context.input.nextLine();
        try {
            AccountType type = AccountType.parse(input);
            accBuilder.setType(type);
        } catch (IllegalArgumentException e) {
            context.output.println("Error: " + e.getMessage());
            return;
        }

        context.output.print("Pick a name for the account: ");
        input = context.input.nextLine();
        try {
            accBuilder.setName(input);
        } catch (IllegalArgumentException e) {
            context.output.println("Error: " + e.getMessage());
            return;
        }

        context.output.print("Enter Starting Balance: £");
        input = context.input.nextLine();
        try {
            var bd = new BigDecimal(input);
            accBuilder.setOpeningBalance(bd);
        } catch (RuntimeException e) {
            context.output.println("Error: " + e.getMessage());
            return;
        }

        context.output.println("Does the account require another signatory?");
        context.output.print("Yes[Y] or No[Any Other Key]: ");
        input = context.input.nextLine();
        switch (input.toLowerCase()) {
            case "y", "yes" -> {
                context.output.print("Enter signature: ");
                input = context.input.nextLine();
                try {
                    accBuilder.setSignatory(input);
                } catch (IllegalArgumentException e) {
                    context.output.println("Error: " + e.getMessage());
                    return;
                }
            }
        }

        try {
            Account acc = accBuilder.build();
            context.userSession.accounts.add(acc);
        } catch (InvalidBuilderException e) {
            context.output.println("Error: " + e.getMessage());
        }
    }
}
