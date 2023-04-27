package gateshead.bluspur.terminalApp;

import gateshead.bluspur.accounts.Account;
import gateshead.bluspur.users.User;

import java.util.List;

/**
 * Class that represents the currently logged-in user's session.
 * Contains a reference to the user and a list of their accounts.
 * The list is a convenience, so we don't need to keep looking up the user's accounts.
 * @author craig
 */
public class UserSession {
    private final User currentUser;
    private final List<Account> accounts;

    public UserSession(User user, List<Account> accounts) {
        this.currentUser = user;
        this.accounts = accounts;
    }

    /**
     * @return The currently logged-in user.
     */
    public User getUser() {
        return currentUser;
    }

    /**
     * @return True if the user has accounts, false otherwise.
     */
    public boolean hasAccounts() {
        return !accounts.isEmpty();
    }

    /**
     * @return The number of accounts the user has.
     */
    public int getAccountsCount() {
        return accounts.size();
    }

    /**
     * @return The list of accounts the user has.
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Gets the account at the specified index.
     * @param i The index of the account to get.
     * @return The account at the specified index.
     */
    public Account getAccount(int i) {
        return accounts.get(i);
    }

    /**
     * Adds an account to the user's list of accounts.
     * @param account The account to add.
     */
    public void addAccount(Account account) {
        accounts.add(account);
    }
}
