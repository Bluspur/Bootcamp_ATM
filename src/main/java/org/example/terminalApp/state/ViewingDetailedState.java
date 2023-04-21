package org.example.terminalApp.state;

import org.example.Command;
import org.example.accounts.Account;
import org.example.accounts.BalanceExceedsOverdraftException;
import org.example.terminalApp.TerminalSession;

import java.math.BigDecimal;
import java.util.EnumSet;

public class ViewingDetailedState extends State {
    private final Account account;

    public ViewingDetailedState(TerminalSession session, Account account) {
        super(session);
        this.account = account;
    }

    private final EnumSet<Command> validCommands
            = EnumSet.of(Command.Add, Command.Withdraw, Command.Back, Command.Transfer, Command.Help, Command.Quit);

    @Override
    public void printContextualInformation() {
        context.printAccountDetailed(account);
    }

    @Override
    public void handleCommand(Command command) {
        switch (command) {
            case View -> context.output.println("Error: Already viewing account.");
            case Add -> addMoney();
            case Withdraw -> withdrawMoney();
            case Transfer -> transferMoney();
            case Help -> context.printHelp(validCommands);
            case Back -> context.changeState(new LoggedInState(context));
            case Quit -> context.stop();
        }
    }

    private void transferMoney() {
        BigDecimal amount = context.pollUserForAmount();
        Account target = context.selectAccount();
        if (target == null) {
            context.output.println("Error: Invalid Account.");
            return;
        }
        try {
            account.subtractFunds(amount);
            target.addFunds(amount);
            context.output.println("Transfer successful.");
        } catch (BalanceExceedsOverdraftException e) {
            context.output.println("Error: " + e.getMessage());
        }
    }

    private void withdrawMoney() {
        BigDecimal amount = context.pollUserForAmount();
        try {
            account.subtractFunds(amount);
        } catch (BalanceExceedsOverdraftException e) {
            context.output.println("Error: " + e.getMessage());
        }
    }

    private void addMoney() {
        BigDecimal amount = context.pollUserForAmount();
        account.addFunds(amount);
    }
}
