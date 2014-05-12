package com.github.monzou.guardman;

import static com.github.monzou.guardman.GuardMan.validators;
import static com.google.common.base.Preconditions.checkNotNull;

import com.github.monzou.grinder.BeanPropertyAccessor;
import com.github.monzou.grinder.util.Beans;
import com.github.monzou.guardman.model.PrefixedViolation;
import com.github.monzou.guardman.model.SimpleViolation;
import com.github.monzou.guardman.model.ValidationSeverity;
import com.github.monzou.guardman.model.Violation;
import com.github.monzou.guardman.model.Violations;
import com.github.monzou.guardman.util.Functions2;
import com.github.monzou.guardman.validator.ValidationContext;
import com.github.monzou.guardman.validator.ValueValidator;
import com.github.monzou.guardman.validator.impl.RequiredValidator;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

/**
 * A mutable context for bean validation which provides utilities and reports violations.
 * 
 * @param <T> the type of bean to validate
 */
public class BeanValidationContext<T> extends Violations {

    private static final long serialVersionUID = 2618934244824336754L;

    /** The prefix of violation message */
    protected final Function<String, String> prefix;

    /** The bean to validate */
    protected final T bean;

    public BeanValidationContext(T bean) {
        this((String) null, bean);
    }

    public BeanValidationContext(String prefix, T bean) {
        this(prefix, bean, new Violations());
    }

    public BeanValidationContext(Function<String, String> prefix, T bean) {
        this(prefix, bean, new Violations());
    }

    public BeanValidationContext(String prefix, T bean, Violations violations) {
        this(prefix == null ? null : Functions2.constant(prefix), bean, violations);
    }

    public BeanValidationContext(Function<String, String> prefix, T bean, Violations violations) {
        super(violations);
        this.prefix = prefix;
        this.bean = checkNotNull(bean);
    }

    public T getBean() {
        return bean;
    }

    public void warn(String message) {
        warn((String) null, message);
    }

    public void warn(String propertyName, String message) {
        addViolation(createWarning(propertyName, message));
    }

    public void warn(BeanPropertyAccessor<? super T, ?> property, String message) {
        addViolation(createWarning(property.getName(), message));
    }

    public void error(String message) {
        error((String) null, message);
    }

    public void error(String propertyName, String message) {
        addViolation(createError(propertyName, message));
    }

    public void error(BeanPropertyAccessor<? super T, ?> property, String message) {
        addViolation(createError(property.getName(), message));
    }

    @SuppressWarnings("unchecked")
    public <V> boolean validate(String propertyName, ValueValidator<? super V> validator) {
        return validate(propertyName, (V) Beans.getProperty(bean, propertyName), validator);
    }

    public <V> boolean validate(BeanPropertyAccessor<? super T, V> property, ValueValidator<? super V> validator) {
        return validate(property.getName(), property.apply(bean), validator);
    }

    public <V> boolean validate(String propertyName, V value, ValueValidator<? super V> validator) {
        return validator.apply(value, new PropertyValidationContext(propertyName));
    }

    private Violation createWarning(String key, String message) {
        return createViolation(ValidationSeverity.WARNING, key, message);
    }

    private Violation createError(String key, String message) {
        return createViolation(ValidationSeverity.ERROR, key, message);
    }

    private Violation createViolation(ValidationSeverity severity, String key, String message) {
        Violation violation = new SimpleViolation(key, severity, message);
        if (prefix != null) {
            violation = new PrefixedViolation(prefix.apply(key), violation);
        }
        return violation;
    }

    public <V> V valueOf(BeanPropertyAccessor<? super T, V> property) {
        return property.apply(bean);
    }

    public <V> PropertyValidation<T, V> property(BeanPropertyAccessor<? super T, V> property) {
        return new PropertyValidation<T, V>(this, property);
    }

    public <V> PropertyValidation<T, V> property(String propertyName) {
        return new PropertyValidation<T, V>(this, new ReflectionBeanPropertyAccessor<V>(propertyName));
    }

    /** Property Validation Helper */
    public static class PropertyValidation<T, V> {

        private final BeanValidationContext<T> context;

        private final BeanPropertyAccessor<? super T, V> property;

        private boolean haltOnError;

        private boolean passed = true;

        protected PropertyValidation(BeanValidationContext<T> context, BeanPropertyAccessor<? super T, V> property) {
            this.context = context;
            this.property = property;
        }

        public PropertyValidation<T, V> haltOnError(boolean haltOnError) {
            this.haltOnError = haltOnError;
            return this;
        }

        public PropertyValidation<T, V> required() {
            return validate(RequiredValidator.INSTANCE);
        }

        public PropertyValidation<T, V> eq(V conditionValue) {
            return validate(GuardMan.eq(conditionValue));
        }

        public PropertyValidation<T, V> eq(BeanPropertyAccessor<? super T, V> conditionProperty) {
            return eq(context.valueOf(conditionProperty));
        }

        public PropertyValidation<T, V> eq(BeanPropertyAccessor<? super T, V> conditionProperty, String conditionPropertyCaption) {
            return eq(context.valueOf(conditionProperty), conditionPropertyCaption);
        }

        public PropertyValidation<T, V> eq(V conditionValue, String conditionPropertyCaption) {
            return validate(GuardMan.eq(conditionValue).params(conditionPropertyCaption));
        }

        public PropertyValidation<T, V> ne(V conditionValue) {
            return validate(GuardMan.ne(conditionValue));
        }

        public PropertyValidation<T, V> ne(BeanPropertyAccessor<? super T, V> conditionProperty) {
            return ne(context.valueOf(conditionProperty));
        }

        public PropertyValidation<T, V> ne(BeanPropertyAccessor<? super T, V> conditionProperty, String conditionPropertyCaption) {
            return ne(context.valueOf(conditionProperty), conditionPropertyCaption);
        }

        public PropertyValidation<T, V> ne(V conditionValue, String conditionPropertyCaption) {
            return validate(GuardMan.ne(conditionValue).params(conditionPropertyCaption));
        }

        public PropertyValidation<T, V> lt(V conditionValue) {
            return validate(GuardMan.lt(conditionValue));
        }

        public PropertyValidation<T, V> lt(BeanPropertyAccessor<? super T, V> conditionProperty) {
            return lt(context.valueOf(conditionProperty));
        }

        public PropertyValidation<T, V> lt(BeanPropertyAccessor<? super T, V> conditionProperty, String conditionPropertyCaption) {
            return lt(context.valueOf(conditionProperty), conditionPropertyCaption);
        }

        public PropertyValidation<T, V> lt(V conditionValue, String conditionPropertyCaption) {
            return validate(GuardMan.lt(conditionValue).params(conditionPropertyCaption));
        }

        public PropertyValidation<T, V> le(V conditionValue) {
            return validate(GuardMan.le(conditionValue));
        }

        public PropertyValidation<T, V> le(BeanPropertyAccessor<? super T, V> conditionProperty) {
            return le(context.valueOf(conditionProperty));
        }

        public PropertyValidation<T, V> le(BeanPropertyAccessor<? super T, V> conditionProperty, String conditionPropertyCaption) {
            return le(context.valueOf(conditionProperty), conditionPropertyCaption);
        }

        public PropertyValidation<T, V> le(V conditionValue, String conditionPropertyCaption) {
            return validate(GuardMan.le(conditionValue).params(conditionPropertyCaption));
        }

        public PropertyValidation<T, V> gt(V conditionValue) {
            return validate(GuardMan.gt(conditionValue));
        }

        public PropertyValidation<T, V> gt(BeanPropertyAccessor<? super T, V> conditionProperty) {
            return gt(context.valueOf(conditionProperty));
        }

        public PropertyValidation<T, V> gt(BeanPropertyAccessor<? super T, V> conditionProperty, String conditionPropertyCaption) {
            return gt(context.valueOf(conditionProperty), conditionPropertyCaption);
        }

        public PropertyValidation<T, V> gt(V conditionValue, String conditionPropertyCaption) {
            return validate(GuardMan.gt(conditionValue).params(conditionPropertyCaption));
        }

        public PropertyValidation<T, V> ge(V conditionValue) {
            return validate(GuardMan.ge(conditionValue));
        }

        public PropertyValidation<T, V> ge(BeanPropertyAccessor<? super T, V> conditionProperty) {
            return ge(context.valueOf(conditionProperty));
        }

        public PropertyValidation<T, V> ge(BeanPropertyAccessor<? super T, V> conditionProperty, String conditionPropertyCaption) {
            return ge(context.valueOf(conditionProperty), conditionPropertyCaption);
        }

        public PropertyValidation<T, V> ge(V conditionValue, String conditionPropertyCaption) {
            return validate(GuardMan.ge(conditionValue).params(conditionPropertyCaption));
        }

        @SafeVarargs
        public final PropertyValidation<T, V> validate(ValueValidator<? super V>... validators) {
            return validate(validators(validators).haltOnError(haltOnError));
        }

        public PropertyValidation<T, V> validate(ValueValidator<? super V> validator) {
            if (passed || !haltOnError) {
                passed &= context.validate(property, validator);
            }
            return this;
        }

        public PropertyValidation<T, V> warn(String message) {
            if (passed || !haltOnError) {
                context.warn(property, message);
            }
            return this;
        }

        public PropertyValidation<T, V> error(String message) {
            if (passed || !haltOnError) {
                context.error(property, message);
                passed = false;
            }
            return this;
        }

        public boolean isPassed() {
            return passed;
        }

        public V getValue() {
            return context.valueOf(property);
        }

    }

    /** {@link BeanPropertyAccessor} implementation with reflection */
    protected class ReflectionBeanPropertyAccessor<V> implements BeanPropertyAccessor<T, V> {

        private final String propertyName;

        protected ReflectionBeanPropertyAccessor(String propertyName) {
            this.propertyName = propertyName;
        }

        /** {@inheritDoc} */
        @Override
        public String getName() {
            return propertyName;
        }

        /** {@inheritDoc} */
        @SuppressWarnings("unchecked")
        @Override
        public V apply(T bean) {
            return (V) Beans.getProperty(bean, propertyName);
        }

    }

    private class PropertyValidationContext implements ValidationContext {

        private final String propertyName;

        PropertyValidationContext(String propertyName) {
            this.propertyName = propertyName;
        }

        /** {@inheritDoc} */
        @Override
        public ValidationContext apply(ValidationSeverity severity, String message) {
            switch (severity) {
            case WARNING:
                BeanValidationContext.this.warn(propertyName, message);
                break;
            case ERROR:
                BeanValidationContext.this.error(propertyName, message);
                break;
            default:
                throw new IllegalArgumentException(String.format("Unexpected severity: %s", severity));
            }
            return this;
        }

    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        ToStringHelper helper = Objects.toStringHelper(String.format("BeanValidationContext<%s>", bean.getClass().getSimpleName()));
        helper.add("violation-size", getViolations().size());
        return helper.toString();
    }
}
