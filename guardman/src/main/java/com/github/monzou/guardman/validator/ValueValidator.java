package com.github.monzou.guardman.validator;

import com.google.common.base.Predicate;

/**
 * ValueValidator
 * 
 * @param <V> the type of value to validate
 */
public interface ValueValidator<V> extends Predicate<V> {

    /**
     * Check the value is valid
     * 
     * @param value the value to check
     * @return <code>true</code> if the given value is valid
     */
    @Override
    boolean apply(V value);

    /**
     * Check the value is valid and apply results to the <code>context</code>
     * 
     * @param value the value to check
     * @param context validation context
     * @return <code>true</code> if the given value is valid
     */
    boolean apply(V value, ValidationContext context);

}
