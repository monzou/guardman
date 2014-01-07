# GuardMan

GuardMan ("Guard man" in Japanese means "Security guard") is a simple validation library for Java.

### Build Status

[![Build Status](https://travis-ci.org/monzou/guardman.png)](https://travis-ci.org/monzou/guardman)

## Installation

1. Add Maven repository: http://monzou.github.com/maven-repository/
2. Add dependency: com.github.monzou:guardman:${version}

Configuration example for Gradle:

```groovy
repositories {
    maven {
        url "http://monzou.github.com/maven-repository/"
    }
}
dependencies {
    compile "com.github.monzou:guardman:${version}"
}
```

## How to use

With [grinder-generator](https://github.com/monzou/grinder) (**Recommended** due to maintenance and performance).

```java
BeanValidationContext<Trade> context = new BeanValidationContext<>(bean);
context.property(TradeMeta.tradeNo).required().validate(
  minLength(0),
  maxLength(10),
  alphaNumeric(false)
);
context.property(TradeMeta.remarks).validate(maxLength(1000));
context.property(TradeMeta.cashFlows).required().validate(notEmpty());
for (CashFlow cashFlow : bean.getCashFlows()) {
    BeanValidationContext<CashFlow> c = new BeanValidationContext<>(String.format("CashFlow %d", cashFlow.getSeqNo()), cashFlow);
    c.property(CashFlowMeta.seqNo).required().validate(max(100));
    c.property(CashFlowMeta.amount).required().validate(
      min(BigDecimal.ZERO),
      max(new BigDecimal("100000000"))
    );
    c.property(CashFlowMeta.startDate).required().lt(CashFlowMeta.endDate);
    c.property(CashFlowMeta.endDate).required();
    context.addViolations("cashFlows", c);
}
for (Violation violation : context) {
    System.out.println(violation.getKey());
    System.out.println(violation.getSeverity());
    System.out.println(violation.getMessage());
}
```

Or, you can use reflection for property access like this:

```java
BeanValidationContext<Trade> context = new BeanValidationContext<>(bean);
context.<String> property("tradeNo").required().validate(
  minLength(0),
  maxLength(10),
  alphaNumeric(false)
);
context.<String> property("remarks").validate(maxLength(1000));
context.<List<CashFlow>> property("cashFlows").required().validate(notEmpty());
for (CashFlow cashFlow : bean.getCashFlows()) {
    BeanValidationContext<CashFlow> c = new BeanValidationContext<>(String.format("CashFlow %d", cashFlow.getSeqNo()), cashFlow);
    c.<Integer> property("seqNo").required().validate(max(100));
    c.<BigDecimal> property("amount").required().validate(
      min(BigDecimal.ZERO),
      max(new BigDecimal("100000000"))
    );
    c.<Date> property("startDate").required().lt(cashFlow.getEndDate());
    c.<Date> property("endDate").required();
    context.addViolations("cashFlows", c);
}
for (Violation violation : context) {
    System.out.println(violation.getKey());
    System.out.println(violation.getSeverity());
    System.out.println(violation.getMessage());
}
```

### Custom Validator

If you want to use custom validator, just implement ```ValueValidator```.

For example:

```java
public class NonZeroValidator extends AbstractMutableValueValidator<Number> {

    /** {@inheritDoc} */
    @Override
    public boolean apply(Number value) {
        return value == null ? true : toBigDecimal(value).compareTo(BigDecimal.ZERO) != 0;
    }

    /** {@inheritDoc} */
    @Override
    protected String resolveMessage(Number value, Object... params) {
        return Messages.get(getClass().getSimpleName());
    }

}
```

## Configuration

### i18n

Configure ```com.github.monzou.guardman.i18n.LocaleProvider``` for translating validation messages.
If you want to customize validation messages, just put ```guardman.properties``` into your classpath.
For example:

* guardman.properties (fallback bundle)
* guardman_en.properties
* guardman_ja.properties

**NOTE: Please use UTF-8 encoding for each properties file.** You don't have to use native2ascii.

## Requirements

* JDK 7 +

## Dependencies

* [Grinder](https://github.com/monzou/grinder)
* [Google Guava](https://code.google.com/p/guava-libraries/)

## License

(The MIT License)

Copyright (c) 2014 Takuro Monji @monzou
