package com.github.monzou.guardman.model;

import com.google.common.base.Function;

/**
 * ViolationFormatter
 */
enum ViolationFormatter implements Function<Violation, String> {

    INSTANCE {
        @Override
        public String apply(Violation v) {
            return String.format("[ %s ] %s | %s", v.getSeverity().name(), v.getKey(), v.getMessage());
        }
    }

}
