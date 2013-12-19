# GuardMan

GuardMan ("Guard man" in Japanese means "Security guard") is a simple validation library for Java.

### Build Status

[![Build Status](https://travis-ci.org/monzou/guardman.png)](https://travis-ci.org/monzou/guardman)

## Installation

1. Add Maven repository: http://monzou.github.com/maven-repository/
2. Add dependency: com.github.monzou:guardman:${version}
3. Add dependency (optional): com.github.monzou:guardman-generator:${version}

Configuration example for Gradle:

```groovy
repositories {
    maven {
        url "http://monzou.github.com/maven-repository/"
    }
}
configurations {
    apt
}
dependencies {
    compile "com.github.monzou:guardman:${version}"
    apt "com.github.monzou:guardman-generator:${version}"
}
```

## How to use

Use ```guardman-generator``` for property access (**Recommended** due to maintenance and performance).

```java
@Guard
public class Trade implements Serializable {

  private String tradeNo;

  private String remarks;

  // ...

}
```

```guardman-generator``` generates bean meta classes:

```java
@SuppressWarnings("serial")
public final class TradeMeta implements java.io.Serializable {

    private static final TradeMeta INSTANCE = new TradeMeta();

    public static TradeMeta get() {
        return INSTANCE;
    }

    public final transient BeanProperty<Trade, String> tradeNo = new BeanProperty<Trade, String>() {

        /** {@inheritDoc} */
        @Override
        public String getName() {
            return "tradeNo";
        }

        /** {@inheritDoc} */
        @Override
        public String apply(Trade bean) {
            return bean.getTradeNo();
        }

    };

    public final transient BeanProperty<Trade, String> remarks = new BeanProperty<Trade, String>() {

        /** {@inheritDoc} */
        @Override
        public String getName() {
            return "remarks";
        }

        /** {@inheritDoc} */
        @Override
        public String apply(Trade bean) {
            return bean.getRemarks();
        }

    };

    // ...

    private Object readResolve() {
        return INSTANCE;
    }

    private TradeMeta() {
    }

}
```

 You can use meta classes like this:

```java
TradeMeta m = TradeMeta.get();
BeanValidationContext<Trade> context = new BeanValidationContext<>(bean);
context.property(m.tradeNo).required().validate(
  minLength(0),
  maxLength(10),
  alphaNumeric(false)
);
context.property(m.remarks).validate(maxLength(1000));
context.property(m.cashFlows).required().validate(notEmpty());
CashFlowMeta cm = CashFlowMeta.get();
for (CashFlow cashFlow : bean.getCashFlows()) {
    BeanValidationContext<CashFlow> c = new BeanValidationContext<>(String.format("CashFlow %d", cashFlow.getSeqNo()), cashFlow);
    c.property(cm.seqNo).required().validate(max(100));
    c.property(cm.amount).required().validate(
      min(BigDecimal.ZERO),
      max(new BigDecimal("100000000"))
    );
    c.property(cm.startDate).required().lt(cm.endDate);
    c.property(cm.endDate).required();
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

### guardman-generator

If you use bean meta classes, configure ```guardman-generator``` as an annotation processor.
For example, if you use Eclipse and Gradle, just configure ```build.gradle``` like following:

```groovy
import groovy.xml.MarkupBuilder
apply plugin: "eclipse"

ext.eclipseProcessorDir = "apt/lib"

configurations {
    apt
}

sourceSets {
    apt
}

dependencies {
    apt "com.github.monzou:guardman-generator:${guardmanVersion}"
}

task compileAptJava(overwrite: true, dependsOn: clean)  {
    doLast {
        sourceSets.apt.output.resourcesDir.mkdirs()
        ant.javac(
            includeAntRuntime: false,
            classpath: configurations.compile.asPath,
            srcdir: "src/main/java",
            encoding: "UTF-8"
        ) {
            compilerarg(line: "-proc:only")
            compilerarg(line: "-processorpath ${configurations.apt.asPath}")
            compilerarg(line: "-s ${sourceSets.apt.output.resourcesDir}")
        }
        compileJava.source sourceSets.apt.output.resourcesDir
    }
    compileJava.dependsOn compileAptJava
}

eclipse {
    jdt {
        file {
            withProperties { properties ->
                properties.setProperty("org.eclipse.jdt.core.compiler.processAnnotations", "enabled")
            }
        }
    }
}

task copyProcessorLibsToEclipse(type: Copy) {
    eclipseJdt.dependsOn copyProcessorLibsToEclipse
    into eclipseProcessorDir
    from configurations.apt
}

def writeFactorySettings() {
    def file = file(".factorypath")
    def writer = new FileWriter(file)
    def xml = new MarkupBuilder(writer)
    xml.setDoubleQuotes(true)
    xml."factorypath"() {
        "factorypathentry"(kind: "PLUGIN", id: "org.eclipse.jst.ws.annotations.core", enabled: true, runInBatchMode: false)
        "factorypathentry"(kind: "WKSPJAR", id: "/${project.name}/${eclipseProcessorDir}/guardman-generator-${guardmanVersion}.jar", enabled: true, runInBatchMode: false)
    }
    writer.close()
}

def writeEclipseSettings() {
    def file = file(".settings/org.eclipse.jdt.apt.core.prefs")
    file.getParentFile().mkdirs()
    def writer = new FileWriter(file)
    writer.write("""
eclipse.preferences.version=1
org.eclipse.jdt.apt.aptEnabled=true
org.eclipse.jdt.apt.genSrcDir=src/main/java
org.eclipse.jdt.apt.reconcileEnabled=true
""")
    writer.close()
}

eclipseJdt {
    doFirst {
        writeFactorySettings()
        writeEclipseSettings()
    }
}

task cleanEclipseSettings {
    cleanEclipse.dependsOn cleanEclipseSettings
    doLast {
        delete ".settings/org.eclipse.jdt.apt.core.prefs"
        delete ".factorypath"
        delete eclipseProcessorDir
    }
}
```

## Requirements

* JDK 7 +

## Dependencies

* [Google Guava](https://code.google.com/p/guava-libraries/)

## License

(The MIT License)

Copyright (c) 2013 Takuro Monji @monzou
