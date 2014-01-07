package com.github.monzou.guardman.validator.impl;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * IntegerValidator
 */
public class IntegerValidator extends PatternValidator implements Serializable {

    private static final long serialVersionUID = -6139436112424624891L;

    private static final Pattern PATTERN = Pattern.compile("^[0-9]+$");

    public IntegerValidator() {
        super(PATTERN);
    }

}
