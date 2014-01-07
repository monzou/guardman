package com.github.monzou.guardman.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.github.monzou.guardman.exception.GuardManRuntimeException;

/**
 * Reflections
 */
final class Reflections {

    static Object invoke(Object o, Method method, Object... args) {
        try {
            return method.invoke(o, args);
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            throw new GuardManRuntimeException(e);
        }
    }

    private Reflections() {
    }

}
