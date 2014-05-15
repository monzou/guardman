package com.github.monzou.guardman.validator.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.github.monzou.guardman.exception.GuardManRuntimeException;
import com.github.monzou.guardman.i18n.Messages;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

/**
 * DigitsValidator
 */
public class DigitsValidator extends AbstractMutableValueValidator<Number> implements Serializable {

    private static enum Errors {

        /** Infinity */
        INFINITY,
        /** Digits */
        DIGITS,

    }

    private static final long serialVersionUID = -5286196467414244181L;

    private final Integer maxIntegerDigits;

    private final Integer maxDecimalDigits;

    public DigitsValidator(int maxIntegerDigits) {
        this(maxIntegerDigits, null);
    }

    public DigitsValidator(int maxIntegerDigits, int maxDecimalDigits) {
        this(Integer.valueOf(maxIntegerDigits), Integer.valueOf(maxDecimalDigits));
    }

    DigitsValidator(Integer maxIntegerDigits, Integer maxDecimalDigits) {
        checkArgument(maxIntegerDigits != null || maxDecimalDigits != null, "maxIntegralDigits or maxDecimalDigits is required");
        this.maxIntegerDigits = maxIntegerDigits;
        this.maxDecimalDigits = maxDecimalDigits;
    }

    /** {@inheritDoc} */
    @Override
    public boolean apply(Number value) {
        return validate(value) == null;
    }

    /** {@inheritDoc} */
    @Override
    protected String resolveMessage(Number value, Object... params) {
        Errors error = validate(value);
        String key = DigitsValidator.class.getSimpleName();
        switch (error) {
        case INFINITY:
            return Messages.get(String.format("%s.infinity", key));
        case DIGITS:
            key = String.format("%s.digits.%s", key, getParamsKey());
            if (params == null || params.length == 0) {
                return Messages.get(key, maxIntegerDigits, maxDecimalDigits);
            } else {
                return Messages.get(key, params);
            }
        default:
            throw new GuardManRuntimeException("Unexpected error: " + error);
        }
    }

    private String getParamsKey() {
        List<String> keys = Lists.newArrayList();
        if (maxIntegerDigits != null) {
            keys.add("integral");
        }
        if (maxDecimalDigits != null) {
            keys.add("decimal");
        }
        return Joiner.on(".").join(keys);
    }

    private Errors validate(Number value) {
        if (value == null || Double.isNaN(value.doubleValue())) {
            return null;
        }
        if (Double.isInfinite(value.doubleValue())) {
            return Errors.INFINITY;
        }
        BigDecimal bd = toBigDecimal(value);
        if (bd == null) {
            return null;
        }
        if (maxIntegerDigits != null) {
            int integerDigits = bd.precision() - bd.scale();
            if (integerDigits > maxIntegerDigits) {
                return Errors.DIGITS;
            }
        }
        if (maxDecimalDigits != null) {
            int decimalDigits = bd.scale() < 0 ? 0 : bd.scale();
            if (decimalDigits > maxDecimalDigits) {
                return Errors.DIGITS;
            }
        }
        return null;
    }

    private BigDecimal toBigDecimal(Number value) {
        if (value == null) {
            return null;
        }
        if (Double.isInfinite(value.doubleValue()) || Double.isNaN(value.doubleValue())) {
            return null;
        }
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        } else {
            return new BigDecimal(value.toString()).stripTrailingZeros();
        }
    }

}
