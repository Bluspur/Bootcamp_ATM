package org.example;

public class User {
    private final Username username;
    private final Password password;

    public User(Username username, Password password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username.toString();
    }

    public boolean verifyUser(String username, String password) {
        return verifyUsername(username)
                && verifyPassword(password);
    }

    private boolean verifyUsername(String username) {
        return this.username.value.equals(username);
    }

    private boolean verifyPassword(String password) {
        return this.password.verify(password);
    }
}