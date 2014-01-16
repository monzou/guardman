package com.github.monzou.guardman.validator.impl;

import java.io.Serializable;

import com.github.monzou.guardman.model.ValidationSeverity;
import com.github.monzou.guardman.validator.MutableValueValidator;
import com.github.monzou.guardman.validator.ValidationContext;

/**
 * AbstractMutableValueValidator
 * 
 * @param <V> the type of value to validate
 */
@SuppressWarnings("serial")
public abstract class AbstractMutableValueValidator<V> implements MutableValueValidator<V>, Serializable {

    private ValidationSeverity severity = ValidationSeverity.ERROR;

    private String message;

    private Object[] params;

    /** {@inheritDoc} */
    @Override
    public boolean apply(V value, ValidationContext context) {
        if (apply(value)) {
            return true;
        }
        if (message != null) {
            context.apply(severity, message);
        } else {
            context.apply(severity, resolveMessage(value, params));
        }
        return false;
    }

    protected abstract String resolveMessage(V value, Object... params);

    /** {@inheritDoc} */
    @Override
    public MutableValueValidator<V> severity(ValidationSeverity severity) {
        this.severity = severity;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public MutableValueValidator<V> message(String message) {
        this.message = message;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public MutableValueValidator<V> params(Object... params) {
        this.params = params;
        return this;
    }

}
