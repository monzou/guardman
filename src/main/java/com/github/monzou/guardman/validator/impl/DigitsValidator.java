package com.github.monzou.guardman.validator.impl;

import java.io.Serializable;
import java.math.BigDecimal;

import com.github.monzou.guardman.exception.GuardManRuntimeException;
import com.github.monzou.guardman.i18n.Messages;

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

    private final int maxIntegerDigits;

    private final int maxDecimalDigits;

    public DigitsValidator(int maxIntegerDigits, int maxDecimalDigits) {
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
            if (params == null || params.length == 0) {
                return Messages.get(String.format("%s.digits", key), maxIntegerDigits, maxDecimalDigits);
            } else {
                return Messages.get(key, params);
            }
        default:
            throw new GuardManRuntimeException("Unexpected error: " + error);
        }
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
        int integerDigits = bd.precision() - bd.scale();
        if (integerDigits > maxIntegerDigits) {
            return Errors.DIGITS;
        }
        int fractionDigits = bd.scale() < 0 ? 0 : bd.scale();
        if (fractionDigits > maxDecimalDigits) {
            return Errors.DIGITS;
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
