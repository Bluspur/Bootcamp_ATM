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
    private final Map<Username, User> usernameUserMap = new HashMap<>();
    private final Map<User, List<Account>> userAccountsMap = new HashMap<>();
    private final File databaseFile;

    public Database(File databaseFile) {
        this.databaseFile = databaseFile;
    }

    public void save() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(databaseFile);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.close();
        fileOutputStream.close();
    }

    public static Database load(File databaseFile) throws IOException, ClassNotFoundException {
        Database database;
        FileInputStream fileInputStream = new FileInputStream(databaseFile);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        database = (Database) objectInputStream.readObject();
        objectInputStream.close();
        fileInputStream.close();
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
