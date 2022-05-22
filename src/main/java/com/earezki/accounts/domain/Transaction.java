package com.earezki.accounts.domain;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

import static com.earezki.accounts.toolkit.Validations.notNull;
import static com.earezki.accounts.toolkit.Validations.positive;

public record Transaction(BigDecimal amount, LocalDateTime date) {

    public Transaction {
        notNull(amount, "Transaction cannot have a null amount");
        notNull(date, "Transaction cannot have a null date");

    }

    public static Transaction newDeposit(BigDecimal amount, Clock clock) {
        return new Transaction(
                positive(
                        notNull(amount, "Deposit cannot have a null amount"),
                        "Deposit amount should be positive"
                ),
                LocalDateTime.now(clock));
    }

    public static Transaction newWithdrawal(BigDecimal amount, Clock clock) {
        positive(
                notNull(amount, "Withdrawal cannot have a null amount"),
                "Withdrawal amount should be positive"
        );

        return new Transaction(amount.negate(), LocalDateTime.now(clock));
    }

    public BigDecimal applyOn(BigDecimal balance) {
        return balance.add(this.amount);
    }

}
