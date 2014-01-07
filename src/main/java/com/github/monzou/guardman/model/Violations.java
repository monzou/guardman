package com.github.monzou.guardman.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

/**
 * Violation Collection
 */
public class Violations implements Iterable<Violation>, Serializable {

    /** Empty Violations */
    public static final Violations EMPTY = new Violations();

    private static final long serialVersionUID = 2363440284829614585L;

    private final Multimap<String, Violation> violationMap;

    public Violations() {
        this(Collections.<Violation> emptyList());
    }

    public Violations(Violations violations) {
        this(violations.getViolations());
    }

    public Violations(Collection<Violation> violations) {
        violationMap = ArrayListMultimap.create();
        for (Violation violation : violations) {
            violationMap.put(violation.getKey(), violation);
        }
    }

    /** {@inheritDoc} */
    @Override
    public Iterator<Violation> iterator() {
        return getViolations().iterator();
    }

    public boolean isEmpty() {
        return violationMap.isEmpty();
    }

    public boolean hasViolations() {
        return !isEmpty();
    }

    public boolean hasErrors() {
        return !getErrors().isEmpty();
    }

    public boolean hasWarnings() {
        return !getWarnings().isEmpty();
    }

    public List<Violation> getViolations() {
        return sortedCopyOf(Lists.newArrayList(violationMap.values()));
    }

    public List<Violation> getWarnings() {
        return getViolations(ValidationSeverity.WARNING);
    }

    public List<Violation> getErrors() {
        return getViolations(ValidationSeverity.ERROR);
    }

    public List<Violation> getViolations(String key) {
        return sortedCopyOf(Lists.newArrayList(violationMap.get(key)));
    }

    public List<Violation> getViolations(final ValidationSeverity severity) {
        return FluentIterable.from(getViolations()).filter(new Predicate<Violation>() {
            @Override
            public boolean apply(Violation violation) {
                return violation == null ? false : violation.getSeverity() == severity;
            }
        }).toList();
    }

    public Violations addViolation(ValidationSeverity severity, String message) {
        return addViolation(null, severity, message);
    }

    public Violations addViolation(String key, ValidationSeverity severity, String message) {
        return addViolation(new SimpleViolation(key, severity, message));
    }

    public Violations addViolation(Violation violation) {
        violationMap.put(checkNotNull(violation).getKey(), violation);
        return this;
    }

    public Violations addViolations(Violations violations) {
        violationMap.putAll(checkNotNull(violations).violationMap);
        return this;
    }

    public Violations addViolations(final String key, Violations violations) {
        return addViolations(checkNotNull(violations).transform(new Function<Violation, Violation>() {
            @Override
            public Violation apply(Violation violation) {
                return new SimpleViolation(String.format("%s.%s", key, violation.getKey()), violation.getSeverity(), violation.getMessage());
            }
        }));
    }

    public Violations transform(Function<Violation, Violation> transformer) {
        return new Violations(FluentIterable.from(getViolations()).transform(transformer).toList());
    }

    public Violations subset(final String key) {
        return new Violations(FluentIterable.from(getViolations()).filter(new Predicate<Violation>() {
            @Override
            public boolean apply(Violation violation) {
                return violation != null && violation.getKey() != null && violation.getKey().startsWith(key);
            }
        }).toList());
    }

    private List<Violation> sortedCopyOf(List<Violation> violations) {
        Collections.sort(violations, Violation.DEFAULT_COMPARATOR);
        return ImmutableList.copyOf(violations);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("violations-size", getViolations().size()).toString();
    }

}
