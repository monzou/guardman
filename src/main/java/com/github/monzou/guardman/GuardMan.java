package com.github.monzou.guardman;

import java.util.Collection;
import java.util.Comparator;
import java.util.regex.Pattern;

import com.github.monzou.guardman.validator.MutableValueValidator;
import com.github.monzou.guardman.validator.ValueValidator;
import com.github.monzou.guardman.validator.impl.AlphaNumericAndSymbolValidator;
import com.github.monzou.guardman.validator.impl.AlphaNumericValidator;
import com.github.monzou.guardman.validator.impl.AlphaValidator;
import com.github.monzou.guardman.validator.impl.CollectionLengthValidator;
import com.github.monzou.guardman.validator.impl.CollectionNotEmptyValidator;
import com.github.monzou.guardman.validator.impl.ComparingValidator;
import com.github.monzou.guardman.validator.impl.ComparingValidator.ComparingOperator;
import com.github.monzou.guardman.validator.impl.DigitsValidator;
import com.github.monzou.guardman.validator.impl.IntegerValidator;
import com.github.monzou.guardman.validator.impl.NotBlankValidator;
import com.github.monzou.guardman.validator.impl.NumericValidator;
import com.github.monzou.guardman.validator.impl.PatternValidator;
import com.github.monzou.guardman.validator.impl.RequiredValidator;
import com.github.monzou.guardman.validator.impl.StringLengthValidator;
import com.github.monzou.guardman.validator.impl.ValueValidators;

/**
 * GuardMan is a simple bean validation library for Java.
 * <p>
 * Let's import GuardMan statically so that the code looks clearer !
 * <p>
 * <h1>RECOMMENDED</h1>
 * You can use grinder-generator as an annotation processor to generate bean meta classes. <br />
 * Then you can use meta classes to validate Java beans.
 * <pre>
 * BeanValidationContext<Trade> context = new BeanValidationContext<Trade>(trade);
 * context.property(TradeMeta.tradeNo).required().validate(
 *   minLength(0),
 *   maxLength(10),
 *   alphaNumeric(false)
 * );
 * context.property(TradeMeta.remarks).validate(maxLength(1000));
 * context.property(TradeMeta.cashFlows).required().validate(notEmpty());
 * for (CashFlow cashFlow : trade.getCashFlows()) {
 *     BeanValidationContext<CashFlow> c = new BeanValidationContext<CashFlow>(String.format("CashFlow %d", cashFlow.getSeqNo()), cashFlow);
 *     c.property(CashFlowMeta.seqNo).required().validate(max(100));
 *     c.property(CashFlowMeta.amount).required().validate(
 *       min(BigDecimal.ZERO),
 *       max(new BigDecimal("100000000"))
 *     );
 *     c.property(CashFlowMeta.startDate).required().lt(CashFlowMeta.endDate, "End Date");
 *     c.property(CashFlowMeta.endDate).required();
 *     context.addViolations("cashFlows", c);
 * }
 * for (Violation violation : context) {
 *   System.out.println(violation.getKey());
 *   System.out.println(violation.getSeverity());
 *   System.out.println(violation.getMessage());
 * }
 * </pre>
 */
public class GuardMan {

    public static final MutableValueValidator<Object> required() {
        return new RequiredValidator();
    }

    public static final <V> MutableValueValidator<V> eq(V conditionValue) {
        return new ComparingValidator<V>(ComparingOperator.EQ, conditionValue);
    }

    public static final <V> MutableValueValidator<V> ne(V conditionValue) {
        return new ComparingValidator<V>(ComparingOperator.NE, conditionValue);
    }

    public static final <V> MutableValueValidator<V> gt(V conditionValue) {
        return new ComparingValidator<V>(ComparingOperator.GT, conditionValue);
    }

    public static final <V> MutableValueValidator<V> gt(V conditionValue, Comparator<V> comparator) {
        return new ComparingValidator<V>(ComparingOperator.GT, conditionValue, comparator);
    }

    public static final <V> MutableValueValidator<V> ge(V conditionValue) {
        return new ComparingValidator<V>(ComparingOperator.GE, conditionValue);
    }

    public static final <V> MutableValueValidator<V> ge(V conditionValue, Comparator<V> comparator) {
        return new ComparingValidator<V>(ComparingOperator.GE, conditionValue, comparator);
    }

    public static final <V> MutableValueValidator<V> lt(V conditionValue) {
        return new ComparingValidator<V>(ComparingOperator.LT, conditionValue);
    }

    public static final <V> MutableValueValidator<V> lt(V conditionValue, Comparator<V> comparator) {
        return new ComparingValidator<V>(ComparingOperator.LT, conditionValue, comparator);
    }

    public static final <V> MutableValueValidator<V> le(V conditionValue) {
        return new ComparingValidator<V>(ComparingOperator.LE, conditionValue);
    }

    public static final <V> MutableValueValidator<V> le(V conditionValue, Comparator<V> comparator) {
        return new ComparingValidator<V>(ComparingOperator.LE, conditionValue, comparator);
    }

    public static final <V> MutableValueValidator<V> min(V conditionValue) {
        return min(conditionValue, true);
    }

    public static final <V> MutableValueValidator<V> min(V conditionValue, boolean allowMin) {
        return allowMin ? ge(conditionValue) : gt(conditionValue);
    }

    public static final <V> MutableValueValidator<V> max(V conditionValue) {
        return max(conditionValue, true);
    }

    public static final <V> MutableValueValidator<V> max(V conditionValue, boolean allowMax) {
        return allowMax ? le(conditionValue) : lt(conditionValue);
    }

    public static final MutableValueValidator<String> notBlank() {
        return new NotBlankValidator();
    }

    public static final MutableValueValidator<String> numeric() {
        return new NumericValidator();
    }

    public static final MutableValueValidator<String> integer() {
        return new IntegerValidator();
    }

    public static final MutableValueValidator<String> alpha(boolean allowSpace) {
        return new AlphaValidator(allowSpace);
    }

    public static final MutableValueValidator<String> alphaNumeric(boolean allowSpace) {
        return new AlphaNumericValidator(allowSpace);
    }

    public static final MutableValueValidator<String> alphaNumericAndSymbol(boolean allowSpace) {
        return new AlphaNumericAndSymbolValidator(allowSpace);
    }

    public static final MutableValueValidator<String> matches(String regex) {
        return matches(regex, 0);
    }

    public static final MutableValueValidator<String> matches(String regex, int flags) {
        return new PatternValidator(Pattern.compile(regex, flags));
    }

    public static final MutableValueValidator<String> length(int length) {
        return new StringLengthValidator(length);
    }

    public static final MutableValueValidator<String> minLength(int min) {
        return minLength(min, true);
    }

    public static final MutableValueValidator<String> minLength(int min, boolean allowMin) {
        return new StringLengthValidator(min, allowMin, null, true);
    }

    public static final MutableValueValidator<String> maxLength(int max) {
        return maxLength(max, true);
    }

    public static final MutableValueValidator<String> maxLength(int max, boolean allowMax) {
        return new StringLengthValidator(null, true, max, allowMax);
    }

    public static final MutableValueValidator<String> minMaxLength(int min, int max) {
        return minMaxLength(min, true, max, true);
    }

    public static final MutableValueValidator<String> minMaxLength(int min, boolean allowMin, int max, boolean allowMax) {
        return new StringLengthValidator(min, allowMin, max, allowMin);
    }

    public static final MutableValueValidator<Collection<?>> notEmpty() {
        return new CollectionNotEmptyValidator();
    }

    public static final MutableValueValidator<Collection<?>> size(int length) {
        return new CollectionLengthValidator(length);
    }

    public static final MutableValueValidator<Collection<?>> minSize(int min) {
        return minSize(min, true);
    }

    public static final MutableValueValidator<Collection<?>> minSize(int min, boolean allowMin) {
        return new CollectionLengthValidator(min, allowMin, null, true);
    }

    public static final MutableValueValidator<Collection<?>> maxSize(int max) {
        return maxSize(max, true);
    }

    public static final MutableValueValidator<Collection<?>> maxSize(int max, boolean allowMax) {
        return new CollectionLengthValidator(null, true, max, allowMax);
    }

    public static final MutableValueValidator<Collection<?>> minMaxSize(int min, int max) {
        return minMaxSize(min, true, max, true);
    }

    public static final MutableValueValidator<Collection<?>> minMaxSize(int min, boolean allowMin, int max, boolean allowMax) {
        return new CollectionLengthValidator(min, allowMin, max, allowMin);
    }

    public static final MutableValueValidator<Number> digits(int maxIntegerDigits, int maxDecimalDigits) {
        return new DigitsValidator(maxIntegerDigits, maxDecimalDigits);
    }

    @SafeVarargs
    public static final <V> ValueValidators<V> validators(ValueValidator<? super V>... validators) {
        return new ValueValidators<V>(validators);
    }

    protected GuardMan() {
    }

}
