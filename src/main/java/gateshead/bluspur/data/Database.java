package gateshead.bluspur.data;

import gateshead.bluspur.accounts.Account;
import gateshead.bluspur.users.Password;
import gateshead.bluspur.users.User;
import gateshead.bluspur.users.Username;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database implements java.io.Serializable{
    private final Map<Username, User> usernameUserMap;
    private final Map<User, List<Account>> userAccountsMap;
    private final File databaseFile;

    public Database(File databaseFile) {
        this.databaseFile = databaseFile;
        usernameUserMap = new HashMap<>();
        userAccountsMap = new HashMap<>();
    }

    // Both save() and load() methods are derived from the following webpage:
    // https://mkyong.com/java/java-serialization-examples/
    public void save() throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(databaseFile);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
        }
    }

    public static Database load(File databaseFile) throws IOException, ClassNotFoundException {
        Database database;
        try (FileInputStream fileInputStream = new FileInputStream(databaseFile);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            database = (Database) objectInputStream.readObject();
        }
        return database;
    }

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
