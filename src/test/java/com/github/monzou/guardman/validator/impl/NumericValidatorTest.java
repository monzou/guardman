package com.github.monzou.guardman.validator.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * NumericValidatorTest
 */
public class NumericValidatorTest {

    @Test
    public void testPattern() {
        PatternValidator subject = new NumericValidator();
        assertTrue(subject.apply(null));
        assertFalse(subject.apply(" "));
        assertFalse(subject.apply("abc"));
        assertFalse(subject.apply("あ"));
        assertTrue(subject.apply("123"));
        assertTrue(subject.apply("123.45"));
        assertTrue(subject.apply("-123.45"));
        assertTrue(subject.apply("+123.45"));
        assertFalse(subject.apply("123a"));
        assertFalse(subject.apply("123."));
    }

}
