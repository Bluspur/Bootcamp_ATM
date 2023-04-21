package org.example.users;

import org.example.users.Username;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsernameTests {
    @Test
    void testValidUsernameDoesNotThrowException() {
        assertDoesNotThrow(() -> new Username("valid"));
    }

    @Test
    void testValidUsernameWithSpacesDoesNotThrowException() {
        assertDoesNotThrow(() -> new Username("Tommy Atkins"));
    }

    @Test
    void testNullUsernameThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Username(null));
    }

    @Test
    void testUsernameLessThanMinimumLengthThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Username(""));
    }

    @Test
    void testUsernameGreaterThanMaximumLengthThrowsIllegalArgumentException() {
        char[] chars = new char[Username.MAXIMUM_LENGTH + 1];
        String longString = new String(chars);

        assertThrows(IllegalArgumentException.class,
                () -> new Username(longString));
    }

    @Test
    void testUsernameContainingIllegalCharactersThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Username("!!Invalid!!"));
    }
}