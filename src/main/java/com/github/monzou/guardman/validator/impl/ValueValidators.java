package com.github.monzou.guardman.validator.impl;

import java.io.Serializable;
import java.util.List;

import com.github.monzou.guardman.validator.ValidationContext;
import com.github.monzou.guardman.validator.ValueValidator;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;

/**
 * Chain of {@link ValueValidator}
 * 
 * @param <V> the type of value to validate
 */
public class ValueValidators<V> implements ValueValidator<V>, Serializable {

    private static final long serialVersionUID = -1241855154489833688L;

    private final List<ValueValidator<? super V>> validators;

    private boolean haltOnError;

    @SafeVarargs
    public ValueValidators(ValueValidator<? super V>... validators) {
        this.validators = Lists.newArrayList(validators);
    }

    public ValueValidators<V> haltOnError(boolean haltOnError) {
        this.haltOnError = haltOnError;
        return this;
    }

    public ValueValidators<V> append(ValueValidator<? super V> validator) {
        if (validator != null) {
            validators.add(validator);
        }
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public boolean apply(V value) {
        return Predicates.and(validators).apply(value);
    }

    /** {@inheritDoc} */
    @Override
    public boolean apply(V value, ValidationContext context) {
        boolean passed = true;
        for (ValueValidator<? super V> validator : validators) {
            passed &= validator.apply(value, context);
            if (haltOnError && !passed) {
                break;
            }
        }
        return passed;
    }

}
