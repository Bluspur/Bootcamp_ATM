package org.example;

import java.util.Objects;

/**
 * The Password class wraps a String class and provides
 * some Password specific validation upon construction.
 * This also lets us handle any password specific security
 * in a single class.
 * By wrapping like this, we avoid the need for validating
 * elsewhere. We can be sure that any Password object is in
 * a valid format.
 * @author craig
 */
public class Password {
    private final String value;
    static final int MINIMUM_LENGTH = 5;
    static final int MAXIMUM_LENGTH = 256;

    public Password(String password) {
        validate(password);
        this.value = password;
    }

    public boolean verify(String other) {
        return Objects.equals(this.value, other);
    }

    private void validate(String password) throws IllegalArgumentException {
        if (password == null)
            throw new IllegalArgumentException("Password must not be null.");
        if (password.length() < MINIMUM_LENGTH)
            throw new IllegalArgumentException("Password must be longer than " + MINIMUM_LENGTH + "characters.");
        if (password.length() > MAXIMUM_LENGTH)
            throw new IllegalArgumentException("Password must be shorter than " + MAXIMUM_LENGTH + "characters.");
    }
}
