package com.earezki.accounts.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatementTest {

    @Test
    void test_printing_a_receipt() {
        Statement statement = new Statement(
                List.of(
                        new Transaction(
                                BigDecimal.valueOf(100), LocalDate.parse("2022-05-15").atStartOfDay()
                        ),
                        new Transaction(
                                BigDecimal.valueOf(-50), LocalDate.parse("2022-05-16").atStartOfDay()
                        ),
                        new Transaction(
                                BigDecimal.valueOf(50), LocalDate.parse("2022-05-17").atStartOfDay()
                        )
                )
        );

        Receipt result = statement.receipt();

        Receipt expected = new Receipt(
                List.of(
                        new ReceiptLine(
                                LocalDate.parse("2022-05-15").atStartOfDay(),
                                BigDecimal.valueOf(100),
                                BigDecimal.valueOf(100)
                        ),
                        new ReceiptLine(
                                LocalDate.parse("2022-05-16").atStartOfDay(),
                                BigDecimal.valueOf(-50),
                                BigDecimal.valueOf(50)
                        ),
                        new ReceiptLine(
                                LocalDate.parse("2022-05-17").atStartOfDay(),
                                BigDecimal.valueOf(50),
                                BigDecimal.valueOf(100)
                        )
                )
        );

        assertEquals(expected, result);
    }

}