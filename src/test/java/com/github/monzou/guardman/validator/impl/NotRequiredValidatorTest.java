package com.github.monzou.guardman.validator.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.monzou.guardman.validator.ValueValidator;

/**
 * NotRequiredValidatorTest
 */
public class NotRequiredValidatorTest {

    @Test
    public void testNullValue() {
        ValueValidator<Object> subject = new NotRequiredValidator();
        assertTrue(subject.apply(null));
    }

    @Test
    public void testNotNullValue() {
        ValueValidator<Object> subject = new NotRequiredValidator();
        assertFalse(subject.apply("foo"));
    }

    @Test
    public void testBlankValue1() {
        ValueValidator<Object> subject = new NotRequiredValidator();
        assertTrue(subject.apply(""));
    }

    @Test
    public void testBlankValue2() {
        ValueValidator<Object> subject = new NotRequiredValidator();
        assertTrue(subject.apply(" "));
    }

    @Test
    public void testMessage() {
        MockValidationContext context = new MockValidationContext();
        ValueValidator<Object> subject = new NotRequiredValidator().message("message");
        subject.apply("foo", context);
        assertThat(context.getErrors().iterator().next(), is("message"));
    }

}
