package com.github.monzou.guardman.validator.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.monzou.guardman.validator.ValueValidator;

/**
 * DigitsValidatorTest
 */
public class DigitsValidatorTest {

    @Test
    public void testNullValue() {
        DigitsValidator subject = new DigitsValidator(10, 5);
        assertTrue(subject.apply(null));
    }

    @Test
    public void testNaN() {
        DigitsValidator subject = new DigitsValidator(10, 5);
        assertTrue(subject.apply(Double.NaN));
    }

    @Test
    public void testDigits() {
        DigitsValidator subject = new DigitsValidator(10, 5);
        assertTrue(subject.apply(1234567890.12345));
        assertFalse(subject.apply(12345678901.12345));
        assertFalse(subject.apply(1234567890.123456));
    }

    @Test
    public void testMessage() {
        MockValidationContext context = new MockValidationContext();
        ValueValidator<Number> subject = new DigitsValidator(10, 5).message("message");
        subject.apply(12345678901.123456, context);
        assertThat(context.getErrors().iterator().next(), is("message"));
    }

}
