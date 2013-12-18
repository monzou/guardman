package com.github.monzou.guardman.validator.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.monzou.guardman.validator.ValueValidator;

/**
 * AlphaValidatorTest
 */
public class AlphaNumericValidatorTest {

    @Test
    public void testPattern() {

        {
            ValueValidator<String> subject = new AlphaNumericValidator(false);
            assertTrue(subject.apply(null));
            assertTrue(subject.apply(""));
            assertFalse(subject.apply(" "));
            assertTrue(subject.apply("abc"));
            assertFalse(subject.apply("a bc"));
            assertTrue(subject.apply("abc1"));
            assertFalse(subject.apply("abc?"));
            assertTrue(subject.apply("123"));
            assertFalse(subject.apply("1 23 "));
            assertFalse(subject.apply("ＡＢＣ"));
            assertFalse(subject.apply("あ"));
        }
        {
            ValueValidator<String> subject = new AlphaNumericValidator(true);
            assertTrue(subject.apply(null));
            assertTrue(subject.apply(""));
            assertTrue(subject.apply(" "));
            assertTrue(subject.apply("abc"));
            assertTrue(subject.apply("a bc"));
            assertTrue(subject.apply("abc1"));
            assertFalse(subject.apply("abc?"));
            assertTrue(subject.apply("123"));
            assertTrue(subject.apply("1 23 "));
            assertFalse(subject.apply("ＡＢＣ"));
            assertFalse(subject.apply("あ"));
        }

    }

}
