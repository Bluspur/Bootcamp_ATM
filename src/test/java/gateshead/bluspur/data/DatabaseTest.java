package gateshead.bluspur.data;

import gateshead.bluspur.users.Password;
import gateshead.bluspur.users.User;
import gateshead.bluspur.users.Username;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    Database testDatabase;
    User testUser;
    Username testUsername;
    Password testPassword;

    @BeforeEach
    void initializeTestDatabase() {
        testUsername = new Username("Joe Blogs");
        testPassword = new Password("myPassword");
        testUser = new User(testUsername, testPassword);

        File databaseFile = new File(System.getProperty("user.home"), "test_database.ser");
        if (databaseFile.exists()) {
            try {
                testDatabase = Database.load(databaseFile);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }
        } else {
            testDatabase = new Database(databaseFile);
            try {
                testDatabase.save();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        try {
            testDatabase.register(testUsername, testPassword);
        } catch (UserAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void clearTestDatabase() {
        testDatabase.clear();
    }

    @Test
    void testGetUserThrowsExceptionWhenUserNotInDatabase() {
        Username badUsername = new Username("Tommy Atkins");

        assertThrows(UserNotFoundException.class, () -> testDatabase.login(badUsername, testPassword));
    }

    @Test
    void testLoginReturnsUserWhenCorrectDetailsGiven() {
        User actualUser;

        try {
            actualUser = testDatabase.login(testUsername, testPassword);
        } catch (UserNotFoundException | InvalidCredentialsException e) {
            throw new RuntimeException(e);
        }

        assertEquals(testUser, actualUser);
    }

    @Test
    void testLoginThrowsExceptionWhenIncorrectPasswordGiven() {
        Password badPassword = new Password("badPass");

        assertThrows(InvalidCredentialsException.class, () -> testDatabase.login(testUsername, badPassword));
    }
}