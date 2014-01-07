package com.github.monzou.guardman.validator.impl;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * CollectionNotEmptyValidatorTest
 */
public class CollectionNotEmptyValidatorTest {

    @Test
    public void testLength() {
        
        CollectionNotEmptyValidator subject = new CollectionNotEmptyValidator();
        assertTrue(subject.apply(null));
        assertFalse(subject.apply(Collections.emptyList()));
        assertTrue(subject.apply(Lists.newArrayList(1)));
        
    }

}
