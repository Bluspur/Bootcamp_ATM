package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsernameTests {
    @Test
    void testValidUsernameDoesNotThrowException() {
        assertDoesNotThrow(() -> new Username("valid"));
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
}