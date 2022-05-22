package com.earezki.accounts.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {

    private Clock clock = Clock.systemUTC();

    @Test
    void test_deposit() {
        Account account = new Account(UUID.randomUUID());

        account.deposit(BigDecimal.valueOf(1000), clock);

        BigDecimal result = account.currentBalance();
        assertEquals(BigDecimal.valueOf(1000), result);
    }

    @Test
    void test_withdraw() {
        Account account = new Account(
                UUID.randomUUID(),
                List.of(new Transaction(BigDecimal.valueOf(1000), LocalDateTime.now(clock)))
        );

        account.withdrawal(BigDecimal.valueOf(900), clock);

        BigDecimal result = account.currentBalance();
        assertEquals(BigDecimal.valueOf(100), result);
    }

    @Test
    void should_not_withdraw_when_funds_required() {
        Account account = new Account(
                UUID.randomUUID(),
                List.of(new Transaction(BigDecimal.valueOf(1000), LocalDateTime.now(clock)))
        );

        Executable withdrawal = () -> account.withdrawal(BigDecimal.valueOf(1100), clock);

        assertThrows(FundsExceededException.class, withdrawal);
    }

    @Test
    void currentBalance_should_accumulate_transactions() {
        Account account = new Account(UUID.randomUUID());

        account.deposit(BigDecimal.valueOf(1000), clock);
        account.withdrawal(BigDecimal.valueOf(900), clock);
        account.deposit(BigDecimal.valueOf(100), clock);

        BigDecimal result = account.currentBalance();
        assertEquals(BigDecimal.valueOf(200), result);
    }

}