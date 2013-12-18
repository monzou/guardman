package com.github.monzou.guardman.validator.impl;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * IntegerValidatorTest
 */
public class IntegerValidatorTest {

    @Test
    public void testPattern() {
        PatternValidator subject = new IntegerValidator();
        assertTrue(subject.apply(null));
        assertFalse(subject.apply(" "));
        assertFalse(subject.apply("abc"));
        assertFalse(subject.apply("あ"));
        assertTrue(subject.apply("1"));
        assertTrue(subject.apply("123"));
        assertFalse(subject.apply("123.45"));
        assertFalse(subject.apply("-123.45"));
        assertFalse(subject.apply("+123.45"));
        assertFalse(subject.apply("123a"));
        assertFalse(subject.apply("123."));
    }

}
