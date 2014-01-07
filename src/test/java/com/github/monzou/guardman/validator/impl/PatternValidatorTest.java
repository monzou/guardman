package com.github.monzou.guardman.validator.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.regex.Pattern;

import org.junit.Test;

import com.github.monzou.guardman.validator.ValueValidator;

/**
 * PatternValidatorTest
 */
public class PatternValidatorTest {

    private static final Pattern PATTERN = Pattern.compile("^\\d{3}$");

    @Test
    public void testNullValue() {
        PatternValidator subject = new PatternValidator(PATTERN);
        assertTrue(subject.apply(null));
    }

    @Test
    public void testBlankValue() {
        PatternValidator subject = new PatternValidator(PATTERN);
        assertTrue(subject.apply(""));
    }

    @Test
    public void testInvalidValue() {
        PatternValidator subject = new PatternValidator(PATTERN);
        assertFalse(subject.apply("1234"));
    }

    @Test
    public void testValidValue() {
        PatternValidator subject = new PatternValidator(PATTERN);
        assertTrue(subject.apply("123"));
    }

    @Test
    public void testMessage() {
        MockValidationContext context = new MockValidationContext();
        ValueValidator<String> subject = new PatternValidator(PATTERN).message("message");
        subject.apply("abc", context);
        assertThat(context.getErrors().iterator().next(), is("message"));
    }

}
