package gateshead.bluspur;

public enum Command {
    Login,
    Logout,
    Register,
    View,
    Open,
    Add,
    Withdraw,
    Transfer,
    List,
    Help,
    Quit;
    public static Command parse(String rawCommand) throws InvalidCommandException {
        rawCommand = rawCommand.toLowerCase();
        return switch (rawCommand) {
            case "l", "login" -> Command.Login;
            case "li", "list" -> Command.List;
            case "lo", "logout" -> Command.Logout;
            case "r", "register" -> Command.Register;
            case "v", "view" -> Command.View;
            case "o", "open" -> Command.Open;
            case "a", "add" -> Command.Add;
            case "w", "withdraw" -> Command.Withdraw;
            case "t", "transfer" -> Command.Transfer;
            case "h", "help" -> Command.Help;
            case "q", "quit" -> Command.Quit;
            default -> throw new InvalidCommandException(rawCommand + " is not a valid command.");
        };
    }

    public static String getHelp(Command command) {
        return switch (command) {
            case Login -> String.format("%-20s| Use to log in to an existing account.\n", "Login[l]");
            case Logout -> String.format("%-20s| Use to log out and return to the welcome screen.\n", "Logout[lo]");
            case List -> String.format("%-20s| Use to list all accounts.\n", "List[li]");
            case Register -> String.format("%-20s| Use to create a new account.\n", "Register[r]");
            case View -> String.format("%-20s| Use to view an account in greater detail.\n", "View[v] Account");
            case Open -> String.format("%-20s| Use to open a new account.\n", "Open[o] Account");
            case Add -> String.format("%-20s| Use to add funds to an account.\n", "Add[a] Funds");
            case Withdraw -> String.format("%-20s| Use to withdraw funds from an account (PROTECTED ACTION).\n", "Withdraw[w] Funds");
            case Transfer -> String.format("%-20s| Use to transfer funds from one account to another (PROTECTED ACTION).\n", "Transfer[t] Funds");
            case Help -> String.format("%-20s| Use to show a list of valid commands.\n", "Help[h]");
            case Quit -> String.format("%-20s| Use to exit the application.\n", "Quit[q]");
        };
    }
}
