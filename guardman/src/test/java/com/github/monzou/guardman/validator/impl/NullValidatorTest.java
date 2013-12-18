package com.github.monzou.guardman.validator.impl;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * NullValidatorTest
 */
public class NullValidatorTest {

    @Test
    public void testNullValue() {
        assertTrue(NullValidator.INSTANCE.apply(null));
    }

    @Test
    public void testNotNullValue() {
        assertTrue(NullValidator.INSTANCE.apply(0));
    }

}
