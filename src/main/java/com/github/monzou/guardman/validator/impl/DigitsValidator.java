package com.github.monzou.guardman.validator.impl;

import java.io.Serializable;
import java.math.BigDecimal;

import com.github.monzou.guardman.i18n.Messages;

/**
 * DigitsValidator
 */
public class DigitsValidator extends AbstractMutableValueValidator<Number> implements Serializable {

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
        if (value == null) {
            return true;
        }
        BigDecimal bd = toBigDecimal(value);
        if (bd == null) {
            return true;
        }
        int integerDigits = bd.precision() - bd.scale();
        if (integerDigits > maxIntegerDigits) {
            return false;
        }
        int fractionDigits = bd.scale() < 0 ? 0 : bd.scale();
        if (fractionDigits > maxDecimalDigits) {
            return false;
        }
        return true;
    }

    /** {@inheritDoc} */
    @Override
    protected String resolveMessage(Number value, Object... params) {
        String key = getClass().getSimpleName();
        if (params == null || params.length == 0) {
            return Messages.get(key, maxIntegerDigits, maxDecimalDigits);
        } else {
            return Messages.get(key, params);
        }
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
