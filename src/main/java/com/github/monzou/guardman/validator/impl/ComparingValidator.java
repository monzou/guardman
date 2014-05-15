package com.github.monzou.guardman.validator.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.util.Comparator;

import com.github.monzou.guardman.i18n.Messages;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.Ordering;

/**
 * ComparingValidator
 * 
 * @param <V> the type of value to validate
 */
public class ComparingValidator<V> extends AbstractMutableValueValidator<V> implements Serializable {

    private static final long serialVersionUID = 3278570841044236049L;

    /** Comparing Operators */
    public static enum ComparingOperator implements Predicate<Integer> {

        /** Equals */
        EQ {
            @Override
            public boolean apply(Integer comparison) {
                return checkNotNull(comparison) == 0;
            }
        },
        /** Not equals */
        NE {
            @Override
            public boolean apply(Integer comparison) {
                return checkNotNull(comparison) != 0;
            }
        },
        /** Greater than */
        GT {
            @Override
            public boolean apply(Integer comparison) {
                return checkNotNull(comparison) > 0;
            }
        },
        /** Greater than or equal to */
        GE {
            @Override
            public boolean apply(Integer comparison) {
                return checkNotNull(comparison) >= 0;
            }
        },
        /** Less than */
        LT {
            @Override
            public boolean apply(Integer comparison) {
                return checkNotNull(comparison) < 0;
            }
        },
        /** Less than or equal to */
        LE {
            @Override
            public boolean apply(Integer comparison) {
                return checkNotNull(comparison) <= 0;
            }
        };

    }

    private static final Comparator<?> DEFAULT_COMPARATOR;
    static {
        DEFAULT_COMPARATOR = Ordering.usingToString().nullsFirst();
    }

    private final ComparingOperator op;

    private final V conditionValue;

    private final Comparator<? super V> comparator;

    public ComparingValidator(ComparingOperator op, V conditionValue) {
        this(op, conditionValue, null);
    }

    public ComparingValidator(ComparingOperator op, V conditionValue, Comparator<? super V> comparator) {
        this.op = checkNotNull(op);
        this.conditionValue = conditionValue;
        this.comparator = comparator;
    }

    /** {@inheritDoc} */
    @Override
    public boolean apply(V value) {
        return new Comparing(value).apply(conditionValue);
    }

    /** {@inheritDoc} */
    @Override
    protected String resolveMessage(V value, Object... params) {
        String key = String.format("%s.%s", ComparingValidator.class.getSimpleName(), op.name().toLowerCase());
        if (params == null || params.length == 0) {
            return Messages.get(key, conditionValue);
        } else {
            return Messages.get(key, params);
        }
    }

    private class Comparing implements Predicate<V> {

        private final V value;

        Comparing(V value) {
            this.value = value;
        }

        /** {@inheritDoc} */
        @Override
        public boolean apply(V conditionValue) {
            switch (op) {
            case EQ:
                return Objects.equal(value, conditionValue);
            case NE:
                return !Objects.equal(value, conditionValue);
            default:
                if (value == null || conditionValue == null) {
                    return true;
                }
                return op.apply(compare(conditionValue));
            }
        }

        @SuppressWarnings({ "unchecked", "rawtypes" })
        private int compare(V conditionValue) {
            if (comparator != null) {
                return comparator.compare(value, conditionValue);
            }
            if (value instanceof Comparable && conditionValue instanceof Comparable) {
                return ((Comparable) value).compareTo((Comparable) conditionValue);
            }
            return ((Comparator<? super V>) DEFAULT_COMPARATOR).compare(value, conditionValue);
        }

    }

}
