package com.github.monzou.guardman.validator.impl;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * NumericValidator
 */
public class NumericValidator extends PatternValidator implements Serializable {

    private static final long serialVersionUID = 3816443069274090337L;

    private static final Pattern PATTERN = Pattern.compile("^((-|\\+)?[0-9]+(\\.[0-9]+)?)+$");

    public NumericValidator() {
        super(PATTERN);
    }

}
