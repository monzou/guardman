package com.github.monzou.guardman.validator.impl;

import java.util.List;

import com.github.monzou.guardman.model.ValidationSeverity;
import com.github.monzou.guardman.validator.ValidationContext;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;

/**
 * MockValidationContext
 */
class MockValidationContext implements ValidationContext {

    private final Multimap<ValidationSeverity, String> messages;

    public MockValidationContext() {
        messages = ArrayListMultimap.create();
    }

    public boolean isEmpty() {
        return messages.isEmpty();
    }

    public boolean hasErrors() {
        return !getErrors().isEmpty();
    }

    public boolean hasWarnings() {
        return !getWarnings().isEmpty();
    }

    public List<String> getErrors() {
        return ImmutableList.copyOf(messages.get(ValidationSeverity.ERROR));
    }

    public List<String> getWarnings() {
        return ImmutableList.copyOf(messages.get(ValidationSeverity.WARNING));
    }

    /** {@inheritDoc} */
    @Override
    public ValidationContext apply(ValidationSeverity severity, String message) {
        messages.put(severity, message);
        return this;
    }

}
