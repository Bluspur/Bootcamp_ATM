package gateshead.bluspur.users;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PasswordTests {
    @Test
    void testValidPasswordDoesNotThrowException() {
        assertDoesNotThrow(() -> new Password("valid"));
    }

    @Test
    void testNullPasswordThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Password(null));
    }

    @Test
    void testPasswordLessThanMinimumLengthThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Password("1234"));
    }

    @Test
    void testPasswordGreaterThanMaximumLengthThrowsIllegalArgumentException() {
        char[] chars = new char[Password.MAXIMUM_LENGTH + 1];
        String longString = new String(chars);

        assertThrows(IllegalArgumentException.class,
                () -> new Password(longString));
    }
}