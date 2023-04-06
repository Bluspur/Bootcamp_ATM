package org.example;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTests {
    @Test
    void testNewAccountBalanceShouldReturnZeroByDefault() {
        Account newAccount = new Account();

        BigDecimal actualBalance = newAccount.getBalance();
        BigDecimal expectedBalance = new BigDecimal("0");
        assertEquals(0, expectedBalance.compareTo(actualBalance));
    }

    @Test
    void testNewAccountBalanceShouldReturnOpeningBalance() {
        Account newAccount = new Account(new BigDecimal("1234.56"));

        BigDecimal expectedBalance = new BigDecimal("1234.56");
        BigDecimal actualBalance = newAccount.getBalance();
        assertEquals(0, expectedBalance.compareTo(actualBalance));
    }

    @Test
    void testAddingMoneyToAccountShouldReturnNewBalance() {
        Account newAccount = new Account();

        BigDecimal balanceToAdd = new BigDecimal("1234.56");
        BigDecimal expectedBalance = new BigDecimal("1234.56");

        newAccount.addFunds(balanceToAdd);
        BigDecimal actualBalance = newAccount.getBalance();

        assertEquals(0, expectedBalance.compareTo(actualBalance));
    }

    @Test
    void testSubtractingMoneyFromAccountShouldReturnNewBalance() {
        Account newAccount = new Account(new BigDecimal("500.00"));

        BigDecimal balanceToSubtract = new BigDecimal("500.00");
        BigDecimal expectedBalance = new BigDecimal("0");

        try {
            newAccount.subtractFunds(balanceToSubtract);
        } catch (BalanceExceedsOverdraftException e) {
            throw new RuntimeException(e);
        }

        BigDecimal actualBalance = newAccount.getBalance();

        assertEquals(0, expectedBalance.compareTo(actualBalance));
    }

    @Test
    void testSubtractingMoreMoneyThanOverdraftLimitShouldThrowException() {
        Account newAccount = new Account();
        BigDecimal balanceToSubtract = new BigDecimal("1500.00");

        assertThrows(BalanceExceedsOverdraftException.class, () -> newAccount.subtractFunds(balanceToSubtract));
    }

    @Test
    void testAddingNegativeFundsShouldThrowException() {
        Account newAccount = new Account();
        BigDecimal negativeValue = new BigDecimal("-1");

        assertThrows(
                IllegalArgumentException.class,
                () -> newAccount.addFunds(negativeValue)
        );
    }

    @Test
    void testSubtractingNegativeFundsShouldThrowException() {
        Account newAccount = new Account();
        BigDecimal negativeValue = new BigDecimal("-1");

        assertThrows(
                IllegalArgumentException.class,
                () -> newAccount.subtractFunds(negativeValue)
        );
    }
}
