package com.github.monzou.guardman.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;

import com.google.common.base.Objects;

/**
 * SimpleViolation
 */
public class SimpleViolation implements Violation, Serializable {

    private static final long serialVersionUID = 7249463011040889894L;

    private final String key;

    private final ValidationSeverity severity;

    private final String message;

    /**
     * Constructor.
     * 
     * @param severity the severity to set
     * @param message the message to set
     */
    public SimpleViolation(ValidationSeverity severity, String message) {
        this(null, severity, message);
    }

    /**
     * Constructor.
     * 
     * @param key the key to set.
     * @param severity the severity to set.
     * @param message the message to set.
     */
    public SimpleViolation(String key, ValidationSeverity severity, String message) {
        this.key = key;
        this.severity = checkNotNull(severity);
        this.message = checkNotNull(message);
    }

    /** {@inheritDoc} */
    @Override
    public ValidationSeverity getSeverity() {
        return severity;
    }

    /** {@inheritDoc} */
    @Override
    public String getKey() {
        return key;
    }

    /** {@inheritDoc} */
    @Override
    public String getMessage() {
        return message;
    }

    /** {@inheritDoc} */
    @Override
    public int compareTo(Violation o) {
        return DEFAULT_COMPARATOR.compare(this, o);
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SimpleViolation) {
            SimpleViolation o = (SimpleViolation) obj;
            return Objects.equal(severity, o.severity) //
                    && Objects.equal(key, o.key) //
                    && Objects.equal(message, o.message);
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return Objects.hashCode(severity, key, message);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return ViolationFormatter.INSTANCE.apply(this);
    }

}