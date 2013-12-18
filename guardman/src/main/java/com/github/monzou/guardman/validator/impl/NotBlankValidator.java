package com.github.monzou.guardman.validator.impl;

import java.io.Serializable;

import com.github.monzou.guardman.i18n.Messages;
import com.github.monzou.guardman.validator.ValueValidator;
import com.google.common.base.Strings;

/**
 * NotBlankValidator
 */
public class NotBlankValidator extends AbstractMutableValueValidator<String> implements Serializable {

    /** the default instance */
    public static ValueValidator<String> INSTANCE = new NotBlankValidator();

    private static final long serialVersionUID = 9070934149591767680L;

    /** {@inheritDoc} */
    @Override
    public boolean apply(String value) {
        return !Strings.isNullOrEmpty(Strings.nullToEmpty(value).trim());
    }

    /** {@inheritDoc} */
    @Override
    protected String resolveMessage(String value, Object... params) {
        return Messages.get(getClass().getSimpleName());
    }

}
