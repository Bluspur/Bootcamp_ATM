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

/**
 * This class is responsible for storing information about users and their accounts.
 * It is also responsible for loading and saving the database to a file.
 * It provides methods for logging in and registering users as well as
 * getting a list of accounts for a given user.
 * @author craig
 */
public class Database implements java.io.Serializable{
    private final Map<Username, User> usernameUserMap;
    private final Map<User, List<Account>> userAccountsMap;
    private final File databaseFile;

    /**
     * Create a new Database object.
     * @param databaseFile the file to save the database to.
     */
    public Database(File databaseFile) {
        this.databaseFile = databaseFile;
        usernameUserMap = new HashMap<>();
        userAccountsMap = new HashMap<>();
    }

    /**
     * Save the database to the file specified in the constructor.
     * Derived from the following webpage:
     * <a href="https://mkyong.com/java/java-serialization-examples/"> Mkyong </a>
     * @throws IOException if there is an error writing to the file.
     */
    public void save() throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(databaseFile);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
        }
    }

    /**
     * Load a Database object from a file.
     * Derived from the following webpage:
     * <a href="https://mkyong.com/java/java-serialization-examples/"> Mkyong </a>
     * @param databaseFile the file to load the database from.
     * @return the Database object loaded from the file.
     * @throws IOException if there is an error reading from the file.
     * @throws ClassNotFoundException if the class of the object read from the file cannot be found.
     */
    public static Database load(File databaseFile) throws IOException, ClassNotFoundException {
        Database database;
        try (FileInputStream fileInputStream = new FileInputStream(databaseFile);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            database = (Database) objectInputStream.readObject();
        }
        return database;
    }

    /**
     * Attempt to log in a user with the given username and password.
     * @param username the username of the user to log in.
     * @param password the password of the user to log in.
     * @return the User object if the user is successfully logged in.
     * @throws UserNotFoundException if the user is not found in the database.
     * @throws InvalidCredentialsException if the username and password do not match the stored username and password.
     */
    public User logIn(Username username, Password password) throws UserNotFoundException, InvalidCredentialsException {
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

    /**
     * Attempt to register a new user with the given username and password.
     * @param username the username of the new user.
     * @param password the password of the new user.
     * @return the User object of the newly registered user.
     * @throws UserAlreadyExistsException if a user with the given username already exists.
     */
    public User register(Username username, Password password) throws UserAlreadyExistsException {
        if (usernameUserMap.containsKey(username))
            throw new UserAlreadyExistsException("User with given username already exists.");

        User user = new User(username, password);

        usernameUserMap.put(username, user);
        userAccountsMap.put(user, new ArrayList<>());

        return user;
    }

    /**
     * Reset the database to its initial state.
     */
    public void clear() {
        usernameUserMap.clear();
        userAccountsMap.clear();
    }

    /**
     * Get a list of accounts for a given user.
     * @param user the user to get the accounts for.
     * @return a list of accounts for the given user.
     */
    public List<Account> getAccountsForUser(User user) {
        return userAccountsMap.get(user);
    }
}
