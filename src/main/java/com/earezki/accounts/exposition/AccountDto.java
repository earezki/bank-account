package com.earezki.accounts.exposition;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

record AccountDto(UUID id, BigDecimal balance, List<ReceiptDto> statement) {
}
