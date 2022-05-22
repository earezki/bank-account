package com.earezki.accounts.toolkit;

public record Tuple<T, U>(T left, U right) {

    public static <T, U> Tuple<T, U> of(T left, U right) {
        return new Tuple<>(left, right);
    }

}