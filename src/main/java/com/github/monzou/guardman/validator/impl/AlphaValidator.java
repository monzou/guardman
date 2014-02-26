package com.github.monzou.guardman.validator.impl;

import java.io.Serializable;
import java.util.regex.Pattern;

import com.github.monzou.guardman.i18n.Messages;

/**
 * AlphaValidator
 */
public class AlphaValidator extends PatternValidator implements Serializable {

    private static final long serialVersionUID = 5271712055692817587L;

    private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z]+$");

    private static final Pattern SPACE_ALLOWED_PATTERN = Pattern.compile("^[a-zA-Z\\s]+$");

    private final boolean allowSpace;

    public AlphaValidator(boolean allowSpace) {
        super(allowSpace ? SPACE_ALLOWED_PATTERN : PATTERN);
        this.allowSpace = allowSpace;
    }

    /** {@inheritDoc} */
    @Override
    protected String resolveMessage(String value, Object... params) {
        String key = AlphaValidator.class.getSimpleName();
        return Messages.get(allowSpace ? key + ".allowSpace" : key);
    }

}
