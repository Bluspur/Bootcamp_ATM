package gateshead.bluspur.terminalApp;

import gateshead.bluspur.accounts.Account;
import gateshead.bluspur.users.User;

import java.util.List;

// The UserSession class currently acts as a convenience class for TerminalSession to access the logged-in user.
// I'm on the fence whether this is a good idea or not.
public class UserSession {
    private final User currentUser;
    private final List<Account> accounts;

    public UserSession(User user, List<Account> accounts) {
        this.currentUser = user;
        this.accounts = accounts;
    }

    public User getUser() {
        return currentUser;
    }

    public boolean hasAccounts() {
        return !accounts.isEmpty();
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public Account getAccount(int i) {
        return accounts.get(i);
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }
}
