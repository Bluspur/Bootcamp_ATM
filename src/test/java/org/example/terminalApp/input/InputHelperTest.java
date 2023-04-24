package org.example.terminalApp.input;

import org.example.terminalApp.TerminalIO;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class InputHelperTest {
    private InputHelper inputHelper;

    // Setup InputHelper object with custom input and System.out.
    void initHelper() {
        initHelper("0.00");
    }

    void initHelper(String mockInput) {
        var mockInputStream = new ByteArrayInputStream(mockInput.getBytes());
        TerminalIO config = new TerminalIO(mockInputStream, System.out);
        inputHelper = new InputHelper(config);
    }

    // Test 1: Test that the getCurrencyAmountFromUser method returns a BigDecimal object
    @Test
    void getCurrencyAmountFromUserReturnsBigDecimal() {
        initHelper("0");
        assertEquals(BigDecimal.class, inputHelper.getPositiveCurrencyAmountFromUser().getClass());
    }

    // Test 2: Test that the getCurrencyAmountFromUser method returns a BigDecimal object
    // with the correct value
    @Test
    void getCurrencyAmountFromUserReturnsCorrectValue() {
        String testValue = "100.00";
        String testWrongValue = "999.99";
        initHelper(testValue + "\n" + testValue);
        assertEquals(new BigDecimal(testValue), inputHelper.getPositiveCurrencyAmountFromUser());
        assertNotEquals(new BigDecimal(testWrongValue), inputHelper.getPositiveCurrencyAmountFromUser());
    }

    // Test 3: Test that the getCurrencyAmountFromUser method can handle input with commas
    @Test
    void getCurrencyAmountFromUserHandlesCommas() {
        String testValue = "1,000,000.00";
        String testValueWithoutCommas = "1000000.00";
        initHelper(testValue);
        assertEquals(new BigDecimal(testValueWithoutCommas), inputHelper.getPositiveCurrencyAmountFromUser());
    }

    // Test 4: Test that the getCurrencyAmountFromUser method rejects invalid input until
    // valid input is provided
    // Invalid inputs: empty string, non-numeric characters, too many decimal places,
    // leading zero, negative value, misplaced commas, misplaced decimal
    @Test
    void getCurrencyAmountFromUserRejectsInvalidInput() {
        String[] invalidInputs = {
                "",
                "abc",
                "100.111",
                "001.00",
                "-100.00",
                "1,00,000.00",
                "100.0.00"
        };
        String validInput = "100.00";

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < invalidInputs.length; i++) {
            stringBuilder.append(invalidInputs[i]).append("\n");
        }
        stringBuilder.append(validInput).append("\n");
        String input = stringBuilder.toString();

        initHelper(input);

        BigDecimal output = inputHelper.getPositiveCurrencyAmountFromUser();
        assertEquals(new BigDecimal(validInput), output);
    }
}