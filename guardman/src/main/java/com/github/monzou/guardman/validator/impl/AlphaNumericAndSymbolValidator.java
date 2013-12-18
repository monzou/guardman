package com.github.monzou.guardman.validator.impl;

import java.io.Serializable;
import java.util.regex.Pattern;

import com.github.monzou.guardman.i18n.Messages;

/**
 * AlphaNumericAndSymbolValidator
 */
public class AlphaNumericAndSymbolValidator extends PatternValidator implements Serializable {

    private static final long serialVersionUID = -5904791209105142340L;

    private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9\\p{Punct}]+$");

    private static final Pattern SPACE_ALLOWED_PATTERN = Pattern.compile("^[a-zA-Z0-9\\p{Punct}\\s]+$");

    private final boolean allowSpace;

    public AlphaNumericAndSymbolValidator(boolean allowSpace) {
        super(allowSpace ? SPACE_ALLOWED_PATTERN : PATTERN);
        this.allowSpace = allowSpace;
    }

    /** {@inheritDoc} */
    @Override
    protected String resolveMessage(String value, Object... params) {
        String key = getClass().getSimpleName();
        return Messages.get(allowSpace ? key + ".allowSpace" : key);
    }

}
