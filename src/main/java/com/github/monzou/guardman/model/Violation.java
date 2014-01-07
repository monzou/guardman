package com.github.monzou.guardman.model;

import java.util.Comparator;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Ordering;

/**
 * Violation
 */
public interface Violation extends Comparable<Violation> {

    /** Key */
    Function<Violation, String> KEY = new Function<Violation, String>() {
        @Override
        public String apply(Violation input) {
            return input == null ? null : input.getKey();
        }
    };

    /** Severity */
    Function<Violation, ValidationSeverity> SEVERITY = new Function<Violation, ValidationSeverity>() {
        @Override
        public ValidationSeverity apply(Violation input) {
            return input == null ? null : input.getSeverity();
        }
    };

    /** Message */
    Function<Violation, String> MESSAGE = new Function<Violation, String>() {
        @Override
        public String apply(Violation input) {
            return input == null ? null : input.getMessage();
        }
    };

    /** Warning */
    Predicate<Violation> WARNING = new Predicate<Violation>() {
        @Override
        public boolean apply(Violation input) {
            return input == null ? false : input.getSeverity() == ValidationSeverity.WARNING;
        }
    };

    /** Error */
    Predicate<Violation> ERROR = new Predicate<Violation>() {
        @Override
        public boolean apply(Violation input) {
            return input == null ? false : input.getSeverity() == ValidationSeverity.ERROR;
        }
    };

    /** Default Comparator */
    Comparator<Violation> DEFAULT_COMPARATOR = //
    Ordering.natural().nullsFirst().onResultOf(SEVERITY).//
            compound(Ordering.natural().nullsFirst().onResultOf(KEY).//
                    compound(Ordering.natural().nullsFirst().onResultOf(MESSAGE)));

    /**
     * Returns the violation key.
     * 
     * @return the key
     */
    String getKey();

    /**
     * Return the violation severity.
     * 
     * @return the severity
     */
    ValidationSeverity getSeverity();

    /**
     * Returns the violation message.
     * 
     * @return the violation message
     */
    String getMessage();

}
