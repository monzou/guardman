package com.github.monzou.guardman.validator.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import com.github.monzou.guardman.validator.ValueValidator;

/**
 * StringLengthValidatorTest
 */
@RunWith(Enclosed.class)
public class StringLengthValidatorTest {

    public static class EQ {

        @Test
        public void testNullValue() {
            ValueValidator<String> subject = new StringLengthValidator(2, 2);
            assertTrue(subject.apply(null));
        }

        @Test
        public void testBlankValue() {
            ValueValidator<String> subject = new StringLengthValidator(2, 2);
            assertTrue(subject.apply(""));
        }

        @Test
        public void testSpaceValue() {
            ValueValidator<String> subject = new StringLengthValidator(2, 2);
            assertFalse(subject.apply(" "));
        }

        @Test
        public void testLength() {
            {
                ValueValidator<String> subject = new StringLengthValidator(2, 2);
                assertFalse(subject.apply("a"));
                assertTrue(subject.apply("ab"));
                assertFalse(subject.apply("abc"));
            }
            {
                ValueValidator<String> subject = new StringLengthValidator(2, false, 2, true);
                assertFalse(subject.apply("a"));
                assertFalse(subject.apply("ab"));
                assertFalse(subject.apply("abc"));
            }
        }

        @Test
        public void testMessage() {
            MockValidationContext context = new MockValidationContext();
            ValueValidator<String> subject = new StringLengthValidator(2, 2).message("message");
            subject.apply("abc", context);
            assertThat(context.getErrors().iterator().next(), is("message"));
        }

    }

    public static class Lower {

        @Test
        public void testLength() {
            {
                ValueValidator<String> subject = new StringLengthValidator(2, false, null, true);
                assertFalse(subject.apply("a"));
                assertFalse(subject.apply("ab"));
                assertTrue(subject.apply("abc"));
                assertTrue(subject.apply("abcdefghijklmnopqrstuvwxyz"));
            }
            {
                ValueValidator<String> subject = new StringLengthValidator(2, true, null, true);
                assertFalse(subject.apply("a"));
                assertTrue(subject.apply("ab"));
                assertTrue(subject.apply("abc"));
                assertTrue(subject.apply("abcdefghijklmnopqrstuvwxyz"));
            }
        }

    }

    public static class Upper {

        @Test
        public void testLength() {
            {
                ValueValidator<String> subject = new StringLengthValidator(null, true, 2, true);
                assertTrue(subject.apply("a"));
                assertTrue(subject.apply("ab"));
                assertFalse(subject.apply("abc"));
                assertFalse(subject.apply("abcdefghijklmnopqrstuvwxyz"));
            }
            {
                ValueValidator<String> subject = new StringLengthValidator(null, true, 2, false);
                assertTrue(subject.apply("a"));
                assertFalse(subject.apply("ab"));
                assertFalse(subject.apply("abc"));
                assertFalse(subject.apply("abcdefghijklmnopqrstuvwxyz"));
            }
        }

    }

    public static class LowerUpper {

        @Test
        public void testLength() {
            {
                ValueValidator<String> subject = new StringLengthValidator(2, 5);
                assertFalse(subject.apply("a"));
                assertTrue(subject.apply("ab"));
                assertTrue(subject.apply("abc"));
                assertTrue(subject.apply("abcd"));
                assertTrue(subject.apply("abcde"));
                assertFalse(subject.apply("abcdef"));
            }
            {
                ValueValidator<String> subject = new StringLengthValidator(2, false, 5, true);
                assertFalse(subject.apply("a"));
                assertFalse(subject.apply("ab"));
                assertTrue(subject.apply("abc"));
                assertTrue(subject.apply("abcd"));
                assertTrue(subject.apply("abcde"));
                assertFalse(subject.apply("abcdef"));
            }
            {
                ValueValidator<String> subject = new StringLengthValidator(2, true, 5, false);
                assertFalse(subject.apply("a"));
                assertTrue(subject.apply("ab"));
                assertTrue(subject.apply("abc"));
                assertTrue(subject.apply("abcd"));
                assertFalse(subject.apply("abcde"));
                assertFalse(subject.apply("abcdef"));
            }
        }

    }

}
