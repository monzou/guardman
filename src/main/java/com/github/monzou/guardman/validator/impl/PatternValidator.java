package com.github.monzou.guardman.validator.impl;

import java.io.Serializable;
import java.util.regex.Pattern;

import com.github.monzou.guardman.i18n.Messages;
import com.google.common.base.Strings;

/**
 * PatternValidator
 */
public class PatternValidator extends AbstractMutableValueValidator<String> implements Serializable {

    private static final long serialVersionUID = -5523802058601299988L;

    private final Pattern pattern;

    public PatternValidator(Pattern pattern) {
        this.pattern = pattern;
    }

    /** {@inheritDoc} */
    @Override
    public boolean apply(String value) {
        if (Strings.isNullOrEmpty(value)) {
            return true;
        }
        return pattern.matcher(value).matches();
    }

    /** {@inheritDoc} */
    @Override
    protected String resolveMessage(String value, Object... params) {
        String key = getMessageKey();
        if (params == null || params.length == 0) {
            return Messages.get(key, pattern);
        } else {
            return Messages.get(key, params);
        }
    }
    
    protected String getMessageKey() {
        return PatternValidator.class.getSimpleName();
    }
    
}
