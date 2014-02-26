package com.github.monzou.guardman.validator.impl;

import java.io.Serializable;
import java.util.Collection;

/**
 * CollectionLengthValidator
 */
public class CollectionLengthValidator extends LengthValidator<Collection<?>> implements Serializable {

    private static final long serialVersionUID = 3031453811482448129L;

    public CollectionLengthValidator(int length) {
        this(length, length);
    }

    public CollectionLengthValidator(Integer min, Integer max) {
        this(min, true, max, true);
    }

    public CollectionLengthValidator(Integer min, boolean allowMin, Integer max, boolean allowMax) {
        super(min, allowMin, max, allowMax);
    }

    /** {@inheritDoc} */
    @Override
    protected int lengthOf(Collection<?> value) {
        return value.size();
    }

    /** {@inheritDoc} */
    @Override
    protected String getMessageKey() {
        return CollectionLengthValidator.class.getSimpleName();
    }

}
