package com.earezki.accounts.domain;

import com.earezki.accounts.toolkit.Lists;
import com.earezki.accounts.toolkit.Tuple;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import static com.earezki.accounts.toolkit.Validations.noNullElement;
import static com.earezki.accounts.toolkit.Validations.notNull;

public record Statement(List<Transaction> transactions) {

    public Statement(List<Transaction> transactions) {
        this.transactions = noNullElement(
                notNull(transactions, "Transactions should be assigned"),
                "Transaction cannot be null");
    }

    public Receipt receipt() {
        List<ReceiptLine> receiptLines = this.transactions.stream()
                .sorted(Comparator.comparing(Transaction::date))
                .reduce(
                        Tuple.of(List.of(), BigDecimal.ZERO),
                        this::accumulateTransaction,
                        (lhs, rhs) -> lhs
                ).left();
        return new Receipt(receiptLines);
    }

    private Tuple<List<ReceiptLine>, BigDecimal> accumulateTransaction(Tuple<List<ReceiptLine>, BigDecimal> tuple,
                                                                       Transaction transaction) {
        List<ReceiptLine> data = tuple.left();
        BigDecimal balance = tuple.right();

        BigDecimal currentBalance = transaction.applyOn(balance);

        ReceiptLine receiptLine = new ReceiptLine(transaction.date(), transaction.amount(), currentBalance);
        return Tuple.of(Lists.of(data, receiptLine), currentBalance);
    }

}
