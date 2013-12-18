package com.github.monzou.guardman.validator.impl;

import java.io.Serializable;
import java.util.regex.Pattern;

import com.github.monzou.guardman.i18n.Messages;

/**
 * AlphaNumericValidator
 */
public class AlphaNumericValidator extends PatternValidator implements Serializable {

    private static final long serialVersionUID = -5197082602126303915L;

    private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");

    private static final Pattern SPACE_ALLOWED_PATTERN = Pattern.compile("^[a-zA-Z0-9\\s]+$");

    private final boolean allowSpace;

    public AlphaNumericValidator(boolean allowSpace) {
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
