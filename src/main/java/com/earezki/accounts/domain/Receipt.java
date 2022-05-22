package com.earezki.accounts.domain;

import java.util.List;

public record Receipt(List<ReceiptLine> lines) {
}
