package com.earezki.accounts.exposition;

import java.math.BigDecimal;

record ExecuteTransactionCommand(TransactionType type, BigDecimal amount) {
}
