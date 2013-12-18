package com.github.monzou.guardman.validator.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.monzou.guardman.validator.ValueValidator;

/**
 * NotBlankValidatorTest
 */
public class NotBlankValidatorTest {

    @Test
    public void testNullValue() {
        NotBlankValidator subject = new NotBlankValidator();
        assertFalse(subject.apply(null));
    }

    @Test
    public void testNotNullValue() {
        NotBlankValidator subject = new NotBlankValidator();
        assertTrue(subject.apply(" fo o"));
    }

    @Test
    public void testBlankValue1() {
        NotBlankValidator subject = new NotBlankValidator();
        assertFalse(subject.apply(" "));
    }

    @Test
    public void testBlankValue2() {
        NotBlankValidator subject = new NotBlankValidator();
        assertFalse(subject.apply("   "));
    }

    @Test
    public void testMessage() {
        MockValidationContext context = new MockValidationContext();
        ValueValidator<String> subject = new NotBlankValidator().message("message");
        subject.apply(null, context);
        assertThat(context.getErrors().iterator().next(), is("message"));
    }

}
