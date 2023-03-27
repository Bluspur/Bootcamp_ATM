package org.example;

/**
 * The Username class wraps a String class and provides
 * some Username specific validation upon construction.
 * By wrapping like this, we avoid the need for validating
 * elsewhere. We can be sure that any Username object is in
 * a valid format.
 * @author craig
 */
public class Username {
    final String value;
    static final int MINIMUM_LENGTH = 1;
    static final int MAXIMUM_LENGTH = 256;

    public Username(String username) {
        validate(username);
        this.value = username;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (o instanceof Username username)
            return value.equals(username.value);

        return false;
    }

    private void validate(String username) throws IllegalArgumentException {
        if (username == null)
            throw new IllegalArgumentException("Username must not be null.");
        if (username.length() < MINIMUM_LENGTH)
            throw new IllegalArgumentException("Username must be longer than " + MINIMUM_LENGTH + "characters.");
        if (username.length() > MAXIMUM_LENGTH)
            throw new IllegalArgumentException("Username must be shorter than " + MAXIMUM_LENGTH + "characters.");
    }
}
