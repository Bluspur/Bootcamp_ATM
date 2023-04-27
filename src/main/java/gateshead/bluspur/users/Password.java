package gateshead.bluspur.users;

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
public class Password implements java.io.Serializable {
    final String value;
    static final int MINIMUM_LENGTH = 5;
    static final int MAXIMUM_LENGTH = 256;

    public Password(String password) {
        validate(password);
        this.value = password;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (o instanceof Password password)
            return value.equals(password.value);

        return false;
    }

    /**
     * Validates the password against the following criteria:
     * <ul>
     *     <li>Must not be null</li>
     *     <li>Must be longer than {@link #MINIMUM_LENGTH} characters</li>
     *     <li>Must be shorter than {@link #MAXIMUM_LENGTH} characters</li>
     * </ul>
     * @param password The password to validate
     * @throws IllegalArgumentException If the password is invalid
     */
    private void validate(String password) throws IllegalArgumentException {
        if (password == null)
            throw new IllegalArgumentException("Password must not be null.");
        if (password.length() < MINIMUM_LENGTH)
            throw new IllegalArgumentException("Password must be longer than " + MINIMUM_LENGTH + " characters.");
        if (password.length() > MAXIMUM_LENGTH)
            throw new IllegalArgumentException("Password must be shorter than " + MAXIMUM_LENGTH + " characters.");
    }
}
