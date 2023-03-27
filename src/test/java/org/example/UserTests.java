package org.example;

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
        String expectedValue = "Tommy Atkins";

        assertEquals(expectedValue, testUser.getUsername());
    }

    @Test
    void testVerifyUserReturnsTrueWhenUsernameAndPasswordCorrect() {
        String inputUsername = "Tommy Atkins";
        String inputPassword = "12345";

        assertTrue(testUser.verifyUser(inputUsername, inputPassword));
    }

    @Test
    void testVerifyUserReturnsFalseForIncorrectUsername() {
        String inputUsername = "bad username";
        String inputPassword = "12345";

        assertFalse(testUser.verifyUser(inputUsername, inputPassword));
    }

    @Test
    void testVerifyUserReturnsFalseForIncorrectPassword() {
        String inputUsername = "Tommy Atkins";
        String inputPassword = "bad password";

        assertFalse(testUser.verifyUser(inputUsername, inputPassword));
    }

}
