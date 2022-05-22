package com.earezki.accounts.domain;

public class FundsExceededException extends IllegalArgumentException {
    public FundsExceededException(String message) {
        super(message);
    }
}
