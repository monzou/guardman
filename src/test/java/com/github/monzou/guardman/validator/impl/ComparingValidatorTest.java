package com.github.monzou.guardman.validator.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import com.github.monzou.guardman.validator.ValueValidator;
import com.github.monzou.guardman.validator.impl.ComparingValidator.ComparingOperator;

/**
 * ComparingValidatorTest
 */
@RunWith(Enclosed.class)
public class ComparingValidatorTest {

    public static class EQ {

        @Test
        public void testConditionNull() {
            ValueValidator<Integer> subject = new ComparingValidator<Integer>(ComparingOperator.EQ, null);
            assertTrue(subject.apply(null));
            assertFalse(subject.apply(0));
        }

        @Test
        public void testConditionNonNull() {
            ValueValidator<Integer> subject = new ComparingValidator<Integer>(ComparingOperator.EQ, 0);
            assertTrue(subject.apply(0));
            assertFalse(subject.apply(1));
            assertFalse(subject.apply(-1));
        }

        @Test
        public void testParams() {

            ValueValidator<Integer> subject1 = new ComparingValidator<Integer>(ComparingOperator.EQ, 0);
            MockValidationContext context1 = new MockValidationContext();
            assertFalse(subject1.apply(1, context1));
            assertThat(context1.getErrors().iterator().next(), is("Must be equal to 0"));

            ValueValidator<Integer> subject2 = new ComparingValidator<Integer>(ComparingOperator.EQ, 0).params("param");
            MockValidationContext context2 = new MockValidationContext();
            assertFalse(subject2.apply(1, context2));
            assertThat(context2.getErrors().iterator().next(), is("Must be equal to param"));

        }

        @Test
        public void testMessage() {
            ValueValidator<Integer> subject = new ComparingValidator<Integer>(ComparingOperator.EQ, 0).message("message");
            MockValidationContext context1 = new MockValidationContext();
            assertFalse(subject.apply(1, context1));
            assertThat(context1.getErrors().iterator().next(), is("message"));
        }

    }

    public static class NE {

        @Test
        public void testConditionNull() {
            ValueValidator<Integer> subject = new ComparingValidator<Integer>(ComparingOperator.NE, null);
            assertFalse(subject.apply(null));
            assertTrue(subject.apply(0));
        }

        @Test
        public void testConditionNonNull() {
            ValueValidator<Integer> subject = new ComparingValidator<Integer>(ComparingOperator.NE, 0);
            assertFalse(subject.apply(0));
            assertTrue(subject.apply(1));
            assertTrue(subject.apply(-1));
        }

    }

    public static class GT {

        @Test
        public void testConditionNull() {
            ValueValidator<Integer> subject = new ComparingValidator<Integer>(ComparingOperator.GT, null);
            assertTrue(subject.apply(null));
            assertTrue(subject.apply(0));
        }

        @Test
        public void testValueNull() {
            ValueValidator<Integer> subject = new ComparingValidator<Integer>(ComparingOperator.GT, 0);
            assertTrue(subject.apply(null));
        }

        @Test
        public void testCompare() {
            ValueValidator<Integer> subject = new ComparingValidator<Integer>(ComparingOperator.GT, 0);
            assertTrue(subject.apply(1));
            assertFalse(subject.apply(0));
            assertFalse(subject.apply(-1));
        }

    }

    public static class GE {

        @Test
        public void testConditionNull() {
            ValueValidator<Integer> subject = new ComparingValidator<Integer>(ComparingOperator.GE, null);
            assertTrue(subject.apply(null));
            assertTrue(subject.apply(0));
        }

        @Test
        public void testValueNull() {
            ValueValidator<Integer> subject = new ComparingValidator<Integer>(ComparingOperator.GE, 0);
            assertTrue(subject.apply(null));
        }

        @Test
        public void testCompare() {
            ValueValidator<Integer> subject = new ComparingValidator<Integer>(ComparingOperator.GE, 0);
            assertTrue(subject.apply(1));
            assertTrue(subject.apply(0));
            assertFalse(subject.apply(-1));
        }

    }

    public static class LT {

        @Test
        public void testConditionNull() {
            ValueValidator<Integer> subject = new ComparingValidator<Integer>(ComparingOperator.LT, null);
            assertTrue(subject.apply(null));
            assertTrue(subject.apply(0));
        }

        @Test
        public void testValueNull() {
            ValueValidator<Integer> subject = new ComparingValidator<Integer>(ComparingOperator.LT, 0);
            assertTrue(subject.apply(null));
        }

        @Test
        public void testCompare() {
            ValueValidator<Integer> subject = new ComparingValidator<Integer>(ComparingOperator.LT, 0);
            assertFalse(subject.apply(1));
            assertFalse(subject.apply(0));
            assertTrue(subject.apply(-1));
        }

    }

    public static class LE {

        @Test
        public void testConditionNull() {
            ValueValidator<Integer> subject = new ComparingValidator<Integer>(ComparingOperator.LE, null);
            assertTrue(subject.apply(null));
            assertTrue(subject.apply(0));
        }

        @Test
        public void testValueNull() {
            ValueValidator<Integer> subject = new ComparingValidator<Integer>(ComparingOperator.LE, 0);
            assertTrue(subject.apply(null));
        }

        @Test
        public void testCompare() {
            ValueValidator<Integer> subject = new ComparingValidator<Integer>(ComparingOperator.LE, 0);
            assertFalse(subject.apply(1));
            assertTrue(subject.apply(0));
            assertTrue(subject.apply(-1));
        }

    }
}
