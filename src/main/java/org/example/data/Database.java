package org.example.data;

import org.example.accounts.Account;
import org.example.users.Password;
import org.example.users.User;
import org.example.users.Username;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    private final Map<Username, User> usernameUserMap = new HashMap<>();
    private final Map<User, List<Account>> userAccountsMap = new HashMap<>();

    public User login(Username username, Password password) throws UserNotFoundException, InvalidCredentialsException {
        User user;
        if (usernameUserMap.containsKey(username)) {
            user = usernameUserMap.get(username);
            if (user.verifyUser(username, password)) {
                return user;
            } else {
                throw new InvalidCredentialsException("Incorrect Username or Password");
            }
        } else {
            throw new UserNotFoundException("User not found in Database.");
        }
    }

    public User register(Username username, Password password) throws UserAlreadyExistsException {
        if (usernameUserMap.containsKey(username))
            throw new UserAlreadyExistsException("User with given username already exists.");

        User user = new User(username, password);

        usernameUserMap.put(username, user);
        userAccountsMap.put(user, new ArrayList<>());

        return user;
    }

    public void clear() {
        usernameUserMap.clear();
        userAccountsMap.clear();
    }

    public List<Account> getAccountsForUser(User user) {
        return userAccountsMap.get(user);
    }
}
