package org.example.users;

import org.example.users.Password;
import org.example.users.User;
import org.example.users.Username;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTests {

    User testUser;

    @BeforeEach
    void initializeTestUser() {
        Username username = new Username("Tommy Atkins");
        Password password = new Password("12345");
        testUser = new User(username, password);
    }

    @Test
    void testGetUsernameReturnsConstructedUsername() {
        Username expectedValue = new Username("Tommy Atkins");

        assertEquals(expectedValue, testUser.getUsername());
    }

    @Test
    void testVerifyUserReturnsTrueWhenUsernameAndPasswordCorrect() {
        Username inputUsername = new Username("Tommy Atkins");
        Password inputPassword = new Password("12345");

        assertTrue(testUser.verifyUser(inputUsername, inputPassword));
    }

    @Test
    void testVerifyUserReturnsFalseForIncorrectUsername() {
        Username inputUsername = new Username("bad username");
        Password inputPassword = new Password("12345");

        assertFalse(testUser.verifyUser(inputUsername, inputPassword));
    }

    @Test
    void testVerifyUserReturnsFalseForIncorrectPassword() {
        Username inputUsername = new Username("Tommy Atkins");
        Password inputPassword = new Password("bad password");

        assertFalse(testUser.verifyUser(inputUsername, inputPassword));
    }

}
