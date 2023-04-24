package org.example.accounts;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTests {
    @Test
    void testAccountBuilderReturnsValidAccount() {
        String name = "Tommy's Account";
        AccountType type = AccountType.CLIENT;
        Account acc;
        AccountBuilder accBuilder = new AccountBuilder();

        accBuilder.setName(name);
        accBuilder.setType(type);

        try {
            acc = accBuilder.build();
        } catch (InvalidBuilderException e) {
            throw new RuntimeException(e);
        }

        assertEquals(name, acc.getName());
        assertEquals(type, acc.getType());
        assertEquals(BigDecimal.ZERO, acc.getBalance());
        assertFalse(acc.hasRestrictions());
    }

    @Test
    void testAccountBuilderNoNameThrowsException() {
        AccountType type = AccountType.CLIENT;
        AccountBuilder accBuilder = new AccountBuilder();

        accBuilder.setType(type);

        assertThrows(InvalidBuilderException.class, accBuilder::build);
    }

    @Test
    void testAccountBuilderNoTypeThrowsException() {
        String name = "Tommy's Account";
        AccountBuilder accBuilder = new AccountBuilder();

        accBuilder.setName(name);

        assertThrows(InvalidBuilderException.class, accBuilder::build);
    }

    Account setupTestAccount() {
        var accBuilder = new AccountBuilder();
        accBuilder.setName("Tommy's Account");
        accBuilder.setType(AccountType.CLIENT);
        accBuilder.setOpeningBalance(new BigDecimal("1234.56"));
        accBuilder.setSignatory("Joe Blogs");
        try {
            return accBuilder.build();
        } catch (InvalidBuilderException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testAccountWithSignatoryVerifySignatoryReturnsTrueForCorrectSignature() {
        String correctSignature = "Joe Blogs";
        Account acc = setupTestAccount();

        assertTrue(acc.verifySignatory(correctSignature));
    }

    @Test
    void testAccountWithSignatoryVerifySignatoryReturnsFalseForIncorrectSignature() {
        String incorrectSignature = "Billy Bob Jones";
        Account acc = setupTestAccount();

        assertFalse(acc.verifySignatory(incorrectSignature));
    }

    @Test
    void testNewAccountBalanceShouldReturnOpeningBalance() {
        Account acc = setupTestAccount();

        BigDecimal expectedBalance = new BigDecimal("1234.56");
        BigDecimal actualBalance = acc.getBalance();
        assertEquals(0, expectedBalance.compareTo(actualBalance));
    }

    @Test
    void testAddingMoneyToAccountShouldReturnNewBalance() {
        Account acc = setupTestAccount();

        BigDecimal balanceToAdd = new BigDecimal("765.44");
        BigDecimal expectedBalance = new BigDecimal("2000.00");

        acc.addFunds(balanceToAdd);
        BigDecimal actualBalance = acc.getBalance();

        assertEquals(0, expectedBalance.compareTo(actualBalance));
    }

    @Test
    void testSubtractingMoneyFromAccountShouldReturnNewBalance() {
        Account acc = setupTestAccount();

        BigDecimal balanceToSubtract = new BigDecimal("1234.56");
        BigDecimal expectedBalance = new BigDecimal("0");

        try {
            acc.withdrawFunds(balanceToSubtract);
        } catch (InsufficientFundsException e) {
            throw new RuntimeException(e);
        }

        BigDecimal actualBalance = acc.getBalance();

        assertEquals(0, expectedBalance.compareTo(actualBalance));
    }

    @Test
    void testSubtractingMoreMoneyThanOverdraftLimitShouldThrowException() {
        Account acc = setupTestAccount();
        BigDecimal balanceToSubtract = new BigDecimal("9999.99");

        assertThrows(
                InsufficientFundsException.class,
                () -> acc.withdrawFunds(balanceToSubtract));
    }

    @Test
    void testAddingNegativeFundsShouldThrowException() {
        Account acc = setupTestAccount();
        BigDecimal negativeValue = new BigDecimal("-1");

        assertThrows(
                IllegalArgumentException.class,
                () -> acc.addFunds(negativeValue)
        );
    }

    @Test
    void testSubtractingNegativeFundsShouldThrowException() {
        Account acc = setupTestAccount();
        BigDecimal negativeValue = new BigDecimal("-1");

        assertThrows(
                IllegalArgumentException.class,
                () -> acc.withdrawFunds(negativeValue)
        );
    }
}
