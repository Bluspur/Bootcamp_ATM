package org.example.terminalApp.input;

import org.example.terminalApp.TerminalConfig;

import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Scanner;

public class InputHelper {
    private final TerminalConfig config;
    private final String DEFAULT_MESSAGE = "Please enter a valid amount";
    // Currency Regex by Tom Persing at https://regexlib.com/REDetails.aspx?regexp_id=196. Modified to remove dollar sign.
    private final String CURRENCY_REGEX
            = "^([1-9][0-9]{0,2}(,[0-9]{3})*(\\.[0-9]{0,2})?|[1-9][0-9]*(\\.[0-9]{0,2})?|0(\\.[0-9]{0,2})?|(\\.[0-9]{1,2})?)$";

    public InputHelper(TerminalConfig config) {
        this.config = config;
    }

    public BigDecimal getPositiveCurrencyAmountFromUser() {
        return getPositiveCurrencyAmountFromUser(DEFAULT_MESSAGE);
    }

    public BigDecimal getPositiveCurrencyAmountFromUser(String message) {
        config.output.print(message + ": £");
        String userInput = config.input.nextLine();
        // Check input matches regex
        if (!userInput.isEmpty() && userInput.matches(CURRENCY_REGEX)) {
            // Remove commas from input
            userInput = userInput.replaceAll(",", "");
            return new BigDecimal(userInput);
        }
        else {
            config.output.println("Error: Invalid input. Please try again.");
            return getPositiveCurrencyAmountFromUser(message);
        }
    }
}
