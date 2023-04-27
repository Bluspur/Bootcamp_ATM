package gateshead.bluspur.users;

import java.util.Objects;

/**
 * User represents a registered application user.
 * It contains a Username and Password object.
 * It also provides a method to verify a given Username and Password
 * against the stored Username and Password.
 * It provides implementations of hashCode() and equals() to allow
 * for easy comparison of User objects.
 * @author craig
 */
public class User implements java.io.Serializable {
    private final Username username;
    private final Password password;

    public User(Username username, Password password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Get the Username object.
     * @return the Username object.
     */
    public Username getUsername() {
        return this.username;
    }

    /**
     * Verify a given Username and Password against the stored Username and Password.
     * @param username
     * @param password
     * @return true if the given Username and Password match the stored Username and Password.
     */
    public boolean verifyUser(Username username, Password password) {
        return verifyUsername(username)
                && verifyPassword(password);
    }

    private boolean verifyUsername(Username username) {
        return this.username.equals(username);
    }

    private boolean verifyPassword(Password password) {
        return this.password.equals(password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof User user) return username.equals(user.username);
        return false;
    }
}