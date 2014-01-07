package com.github.monzou.guardman.validator.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

/**
 * ValueValidatorsTest
 */
public class ValueValidatorsTest {

    private ValueValidators<String> subject;

    @Before
    public void setUp() {
        subject = new ValueValidators<String>( //
                new RequiredValidator(), //
                new PatternValidator(Pattern.compile("^\\d{3}$")), //
                new StringLengthValidator(3));
    }

    @Test
    public void testNullValue() {
        assertFalse(subject.apply(null));
    }

    @Test
    public void testInvalidValue() {
        MockValidationContext context = new MockValidationContext();
        assertFalse(subject.apply("abcde", context));
        assertThat(context.getErrors().size(), is(2));
    }

    @Test
    public void testValidValue() {
        assertTrue(subject.apply("123"));
    }

    @Test
    public void testHaltOnError() {
        MockValidationContext context = new MockValidationContext();
        assertFalse(subject.haltOnError(true).apply("abcde", context));
        assertThat(context.getErrors().size(), is(1));
    }

}
