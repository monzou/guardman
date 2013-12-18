package com.github.monzou.guardman.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;

import com.github.monzou.guardman.i18n.Messages;
import com.google.common.base.Objects;

/**
 * PrefixedViolation
 */
public class PrefixedViolation implements Violation, Serializable {

    private static final long serialVersionUID = 3865471601562195616L;

    private final String prefix;

    private final Violation delegate;

    public PrefixedViolation(String prefix, Violation delegate) {
        this.prefix = checkNotNull(prefix);
        this.delegate = checkNotNull(delegate);
    }

    /** {@inheritDoc} */
    @Override
    public String getKey() {
        return delegate.getKey();
    }

    /** {@inheritDoc} */
    @Override
    public ValidationSeverity getSeverity() {
        return delegate.getSeverity();
    }

    /** {@inheritDoc} */
    @Override
    public String getMessage() {
        return Messages.get("violation.format.prefixed", prefix, delegate.getMessage());
    }

    /** {@inheritDoc} */
    @Override
    public int compareTo(Violation o) {
        return DEFAULT_COMPARATOR.compare(this, o);
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PrefixedViolation) {
            PrefixedViolation o = (PrefixedViolation) obj;
            return Objects.equal(prefix, o.prefix) //
                    && Objects.equal(delegate, o.delegate);
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return Objects.hashCode(prefix, delegate);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return ViolationFormatter.INSTANCE.apply(this);
    }

}
