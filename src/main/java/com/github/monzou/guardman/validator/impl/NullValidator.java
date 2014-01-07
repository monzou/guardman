package com.github.monzou.guardman.validator.impl;

import com.github.monzou.guardman.validator.ValidationContext;
import com.github.monzou.guardman.validator.ValueValidator;

/**
 * NullValidator
 */
public enum NullValidator implements ValueValidator<Object> {

    /** singleton instance */
    INSTANCE;

    /** {@inheritDoc} */
    @Override
    public boolean apply(Object input) {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public boolean apply(Object value, ValidationContext context) {
        return true;
    }

}
