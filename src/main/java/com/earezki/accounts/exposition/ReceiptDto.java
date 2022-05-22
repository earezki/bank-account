package com.earezki.accounts.exposition;

import java.math.BigDecimal;
import java.time.LocalDateTime;

record ReceiptDto(LocalDateTime date,
                  BigDecimal transactionAmount,
                  BigDecimal balance) {
}