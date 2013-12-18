package com.github.monzou.guardman.model;

import com.google.common.base.Function;

/**
 * Bean property meta data.
 * 
 * @param <T> the type of the bean which this property belongs to
 * @param <V> the type of this property
 */
public interface BeanProperty<T, V> extends Function<T, V> {

    /**
     * Returns the name of this property.
     * 
     * @return the name of this property
     */
    String getName();

    /**
     * Returns the value of this property.
     * 
     * @param bean the bean
     * @return the value of this property
     */
    @Override
    V apply(T bean);

}
