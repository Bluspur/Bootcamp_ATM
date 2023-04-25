package org.example.terminalApp.state;

import org.example.Command;
import org.example.accounts.*;
import org.example.terminalApp.TerminalSession;
import org.example.terminalApp.TransactionOutcome;
import org.example.users.Username;

import java.math.BigDecimal;
import java.util.EnumSet;

public class LoggedInState extends State {
    private final EnumSet<Command> validCommands = EnumSet.of(
            Command.Add,
            Command.Withdraw,
            Command.Transfer,
            Command.Open,
            Command.Logout,
            Command.View,
            Command.List,
            Command.Help,
            Command.Quit);

    public LoggedInState(TerminalSession session) {
        super(session);
        if (context.userSession.hasAccounts())
            context.printAccountsTable();
    }

    @Override
    public void printContextualInformation() {
        if (!context.userSession.hasAccounts())
            context.printMessage("You have no open accounts. Open[o] an account to continue.");
    }

    @Override
    public void handleCommand(Command command) {
        switch (command) {
            case Open -> context.openNewAccount();
            case Logout -> context.logout();
            case View -> context.printAccountInfo();
            case List -> context.printAccountsTable();
            case Add -> addFundsToAccount();
            case Withdraw -> withdrawFundsFromAccount();
            case Transfer -> transferFunds();
            case Help -> context.printHelp(validCommands);
            case Quit -> context.stop();
            default -> context.printMessage("Error: " + command
                    + " is not possible before logging in.");
        }
    }

    private void addFundsToAccount() {
        Account account = context.getAccountFromCurrentUser("Enter account number");
        BigDecimal amount = context.inputHelper.getPositiveCurrencyAmountFromUser("Amount");
        account.addFunds(amount);
        context.printMessage("Funds added successfully.");
    }

    private void withdrawFundsFromAccount() {
        Account account = context.getAccountFromCurrentUser("Enter account number");
        BigDecimal amount = context.inputHelper.getPositiveCurrencyAmountFromUser("Amount");
        TransactionOutcome outcome = tryWithdraw(account, amount);
        switch (outcome) {
            case SUCCESS -> context.printMessage("Succeeded");
            case INSUFFICIENT_FUNDS -> context.printMessage("Failed: Insufficient funds.");
            case INSUFFICIENT_PRIVILEGES -> context.printMessage("Failed: Insufficient privileges.");
        }
    }

    private TransactionOutcome tryWithdraw(Account account, BigDecimal amount) {
        if (account.hasRestrictions()) {
            context.printMessage("This action requires a signature.");
            Username signature = context.inputHelper.getUsernameFromUser("Enter signature");
            if (account.verifySignatory(signature)) {
                context.printMessage("Signature verified.");
            } else {
                return TransactionOutcome.INSUFFICIENT_PRIVILEGES;
            }
        }

        try {
            account.withdrawFunds(amount);
            return TransactionOutcome.SUCCESS;
        } catch (InsufficientFundsException e) {
            return TransactionOutcome.INSUFFICIENT_FUNDS;
        }
    }

    private void transferFunds() {
        Account source = context.getAccountFromCurrentUser("Enter source account number");
        Account target = context.getAccountFromCurrentUser("Enter target account number");
        BigDecimal amount = context.inputHelper.getPositiveCurrencyAmountFromUser("Amount");
        TransactionOutcome withdrawOutcome = tryWithdraw(source, amount);
        switch (withdrawOutcome) {
            case SUCCESS -> {
                target.addFunds(amount);
                context.printMessage("Succeeded");
            }
            case INSUFFICIENT_FUNDS -> context.printMessage("Failed: Insufficient funds.");
            case INSUFFICIENT_PRIVILEGES -> context.printMessage("Failed: Insufficient privileges.");
        }
    }
}
