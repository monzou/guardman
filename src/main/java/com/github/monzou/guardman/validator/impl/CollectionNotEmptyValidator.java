package com.github.monzou.guardman.validator.impl;

import java.io.Serializable;
import java.util.Collection;

import com.github.monzou.guardman.i18n.Messages;

/**
 * CollectionNotEmptyValidator
 */
public class CollectionNotEmptyValidator extends CollectionLengthValidator implements Serializable {

    private static final long serialVersionUID = -5851392557546971572L;

    public CollectionNotEmptyValidator() {
        super(0, false, null, false);
    }

    /** {@inheritDoc} */
    @Override
    protected String resolveMessage(Collection<?> value, Object... params) {
        return Messages.get(getClass().getSimpleName());
    }

}
