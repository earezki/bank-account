package com.earezki.accounts.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReceiptLine(LocalDateTime date,
                   BigDecimal transactionAmount,
                   BigDecimal balance) {
}
