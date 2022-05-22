package com.earezki.accounts.toolkit;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public final class Validations {

    private Validations() {
        //no-op
    }

    public static <T> T notNull(T data, String message) {
        if (data == null) {
            throw new IllegalArgumentException(message);
        }

        return data;
    }

    public static BigDecimal positive(BigDecimal number, String message) {
        if (number.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(message);
        }

        return number;
    }


    public static <T> List<T> noNullElement(List<T> list, String message) {
        if (list.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException(message);
        }

        return list;
    }


}
