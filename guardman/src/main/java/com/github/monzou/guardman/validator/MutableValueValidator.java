package com.github.monzou.guardman.validator;

import com.github.monzou.guardman.model.ValidationSeverity;

/**
 * Configurable {@link ValueValidator}
 * 
 * @param <V> the type of value to validate
 */
public interface MutableValueValidator<V> extends ValueValidator<V> {

    MutableValueValidator<V> severity(ValidationSeverity severity);

    MutableValueValidator<V> message(String message);

    MutableValueValidator<V> params(Object... params);

}
