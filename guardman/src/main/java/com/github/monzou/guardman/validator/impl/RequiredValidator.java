package com.github.monzou.guardman.validator.impl;

import java.io.Serializable;

import com.github.monzou.guardman.i18n.Messages;
import com.github.monzou.guardman.validator.ValueValidator;

/**
 * RequiredValidator
 */
public class RequiredValidator extends AbstractMutableValueValidator<Object> implements Serializable {

    /** the default instance */
    public static ValueValidator<Object> INSTANCE = new RequiredValidator();

    private static final long serialVersionUID = 903511737289209144L;

    /** {@inheritDoc} */
    @Override
    public boolean apply(Object value) {
        if (value instanceof String) {
            return NotBlankValidator.INSTANCE.apply((String) value);
        }
        return value != null;
    }

    /** {@inheritDoc} */
    @Override
    protected String resolveMessage(Object value, Object... params) {
        return Messages.get(getClass().getSimpleName());
    }

}
