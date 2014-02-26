package com.github.monzou.guardman.validator.impl;

import java.io.Serializable;

import com.github.monzou.guardman.i18n.Messages;
import com.github.monzou.guardman.validator.ValueValidator;

/**
 * NotRequiredValidator
 */
public class NotRequiredValidator extends AbstractMutableValueValidator<Object> implements Serializable {

    /** the default instance */
    public static final ValueValidator<Object> INSTANCE = new NotRequiredValidator();

    private static final long serialVersionUID = -2807955012404008792L;

    /** {@inheritDoc} */
    @Override
    public boolean apply(Object value) {
        return !RequiredValidator.INSTANCE.apply(value);
    }

    /** {@inheritDoc} */
    @Override
    protected String resolveMessage(Object value, Object... params) {
        return Messages.get(NotRequiredValidator.class.getSimpleName());
    }

}
