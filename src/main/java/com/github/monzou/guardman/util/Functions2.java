package com.github.monzou.guardman.util;

import com.google.common.base.Function;

/**
 * Functions2
 */
public final class Functions2 {

    public static <T> Function<T, T> constant(final T value) {
        return new Function<T, T>() {
            @Override
            public T apply(T input) {
                return value;
            }
        };
    }

    private Functions2() {
    }

}
