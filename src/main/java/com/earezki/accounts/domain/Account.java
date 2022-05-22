package com.earezki.accounts.domain;

import com.earezki.accounts.toolkit.Lists;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.List;
import java.util.UUID;

import static com.earezki.accounts.toolkit.Validations.noNullElement;
import static com.earezki.accounts.toolkit.Validations.notNull;
import static java.util.Collections.unmodifiableList;

public class Account {

    private UUID id;

    private List<Transaction> transactions;

    public Account(UUID id) {
        this(id, List.of());
    }

    public Account(UUID id, List<Transaction> transactions) {
        this.id = id;
        this.transactions = noNullElement(
                notNull(transactions, "Transactions should be assigned"),
                "Transaction cannot be null");
    }

    public void deposit(BigDecimal amount, Clock clock) {
        Transaction transaction = Transaction.newDeposit(amount, clock);
        this.transactions = Lists.of(this.transactions, transaction);
    }

    public void withdrawal(BigDecimal amount, Clock clock) {
        Transaction transaction = Transaction.newWithdrawal(amount, clock);
        List<Transaction> newTransactions = Lists.of(this.transactions, transaction);

        BigDecimal balance = applyTransactions(newTransactions);
        if (!haveFunds(balance)) {
            throw new FundsExceededException("Account doesn't have the funds to withdraw [" + amount + "]!");
        }

        this.transactions = newTransactions;
    }

    private boolean haveFunds(BigDecimal balance) {
        return balance.compareTo(BigDecimal.ZERO) >= 0;
    }

    public BigDecimal currentBalance() {
        return applyTransactions(transactions);
    }

    private BigDecimal applyTransactions(List<Transaction> transactions) {
        BigDecimal initial = BigDecimal.ZERO;
        return transactions.stream()
                .reduce(initial, this::applyTransaction, (lhs, rhs) -> rhs);
    }

    private BigDecimal applyTransaction(BigDecimal balance, Transaction transaction) {
        return transaction.applyOn(balance);
    }

    public Statement statement() {
        return new Statement(unmodifiableList(this.transactions));
    }

    public UUID id() {
        return id;
    }
}
