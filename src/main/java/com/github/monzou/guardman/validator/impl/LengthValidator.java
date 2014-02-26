package com.github.monzou.guardman.validator.impl;

import java.io.Serializable;
import java.util.Objects;

import com.github.monzou.guardman.i18n.Messages;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

/**
 * LengthValidator
 * 
 * @param <V> the type of value to validate
 */
public abstract class LengthValidator<V> extends AbstractMutableValueValidator<V> implements Serializable {

    private static final long serialVersionUID = 3889224557437927473L;

    private final Boundary lower;

    private final Boundary upper;

    public LengthValidator(int length) {
        this(length, length);
    }

    public LengthValidator(Integer min, Integer max) {
        this(min, true, max, true);
    }

    public LengthValidator(Integer min, boolean allowMin, Integer max, boolean allowMax) {
        lower = min == null ? null : new Boundary(BoundaryDirection.LOWER, min, allowMin);
        upper = max == null ? null : new Boundary(BoundaryDirection.UPPER, max, allowMax);
    }

    /** {@inheritDoc} */
    @Override
    public boolean apply(V value) {
        if (value == null) {
            return true;
        }
        int length = lengthOf(value);
        if (lower != null && upper != null && lower.equals(upper)) {
            return length == lower.toConditionValue();
        }
        boolean b = true;
        if (lower != null) {
            b &= Integer.compare(length, lower.toConditionValue()) >= 0;
        }
        if (upper != null) {
            b &= Integer.compare(length, upper.toConditionValue()) <= 0;
        }
        return b;
    }

    /** {@inheritDoc} */
    @Override
    protected String resolveMessage(V value, Object... params) {
        String key = resolveMessageKey();
        if (params == null || params.length == 0) {
            return Messages.get(key, (Object[]) FluentIterable.from( //
                    Lists.newArrayList(lower == null ? null : lower.conditionValue, upper == null ? null : upper.conditionValue)).filter(Predicates.notNull()).toArray(Integer.class));
        } else {
            return Messages.get(key, params);
        }
    }

    private String resolveMessageKey() {
        String key = getMessageKey();
        if (lower != null && upper != null && lower.equals(upper)) {
            key += ".eq";
        } else {
            if (lower != null) {
                key += lower.toParam();
            }
            if (upper != null) {
                key += upper.toParam();
            }
        }
        return key;
    }

    protected String getMessageKey() {
        return LengthValidator.class.getSimpleName();
    }

    protected abstract int lengthOf(V value);

    private static enum BoundaryDirection {

        LOWER, UPPER;

    }

    private static class Boundary implements Comparable<Boundary> {

        private final BoundaryDirection direction;

        private final int conditionValue;

        private final boolean allowConditionValue;

        Boundary(BoundaryDirection direction, int conditionValue, boolean allowConditionValue) {
            this.direction = direction;
            this.conditionValue = conditionValue;
            this.allowConditionValue = allowConditionValue;
        }

        /** {@inheritDoc} */
        @Override
        public int compareTo(Boundary o) {
            return Integer.compare(toConditionValue(), o.toConditionValue());
        }

        /** {@inheritDoc} */
        @Override
        public int hashCode() {
            return Objects.hash(toConditionValue());
        }

        /** {@inheritDoc} */
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Boundary) {
                return compareTo((Boundary) obj) == 0;
            }
            return false;
        }

        String toParam() {
            switch (direction) {
            case LOWER:
                return allowConditionValue ? ".ge" : ".gt";
            case UPPER:
                return allowConditionValue ? ".le" : ".lt";
            default:
                throw new IllegalStateException();
            }
        }

        int toConditionValue() {
            switch (direction) {
            case LOWER:
                return allowConditionValue ? conditionValue : conditionValue + 1;
            case UPPER:
                return allowConditionValue ? conditionValue : conditionValue - 1;
            default:
                throw new IllegalStateException();
            }
        }

    }

}
