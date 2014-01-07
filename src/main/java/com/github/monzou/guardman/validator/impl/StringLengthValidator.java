package com.github.monzou.guardman.validator.impl;

import com.google.common.base.Strings;

/**
 * StringLengthValidator
 */
public class StringLengthValidator extends LengthValidator<String> {

    private static final long serialVersionUID = -4146859507441955710L;

    public StringLengthValidator(int length) {
        this(length, length);
    }

    public StringLengthValidator(Integer min, Integer max) {
        this(min, true, max, true);
    }

    public StringLengthValidator(Integer min, boolean allowMin, Integer max, boolean allowMax) {
        super(min, allowMin, max, allowMax);
    }

    /** {@inheritDoc} */
    @Override
    public boolean apply(String value) {
        if (Strings.isNullOrEmpty(value)) {
            return true;
        }
        return super.apply(value);
    }

    /** {@inheritDoc} */
    @Override
    protected int lengthOf(String value) {
        return value.length();
    }

}
