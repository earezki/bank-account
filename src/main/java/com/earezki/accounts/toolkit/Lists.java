package com.earezki.accounts.toolkit;

import java.util.List;
import java.util.stream.Stream;

public final class Lists {

    public Lists() {
        // no-op
    }

    public static <T> List<T> of(List<T> list, T element) {
        return of(list, List.of(element));
    }

    @SafeVarargs
    public static <T> List<T> of(List<T>... lists) {
        return Stream.of(lists)
                .flatMap(List::stream)
                .toList();
    }

}
