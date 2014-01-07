package com.github.monzou.guardman.validator;

import com.github.monzou.guardman.model.ValidationSeverity;

/**
 * ValidationContext
 */
public interface ValidationContext {

    ValidationContext apply(ValidationSeverity severity, String message);

}
