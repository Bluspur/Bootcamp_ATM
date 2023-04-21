package org.example.terminalApp;

import org.example.accounts.Account;
import org.example.users.User;

import java.util.List;

// The UserSession class currently acts as a convenience class for TerminalSession to access the logged-in user.
// I'm on the fence whether this is a good idea or not.
public class UserSession {
    public User currentUser;
    public List<Account> accounts;

    public String getCurrentUser() {
        if (currentUser == null)
            return "NO_USER";
        else
            return currentUser.getUsername().toString();
    }
}
