package org.example.users;

import java.util.Objects;

public class User implements java.io.Serializable {
    private final Username username;
    private final Password password;

    public User(Username username, Password password) {
        this.username = username;
        this.password = password;
    }

    public Username getUsername() {
        return this.username;
    }

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