package com.github.monzou.guardman.i18n;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Locale;

import com.google.common.base.Supplier;

/**
 * {@link Locale} provider for GuardMan
 */
public final class LocaleProvider {

    private static final Supplier<Locale> DEFAULT_SUPPLIER = new Supplier<Locale>() {
        @Override
        public Locale get() {
            return Locale.getDefault();
        }
    };

    private static volatile Supplier<Locale> SUPPLIER = DEFAULT_SUPPLIER;

    public static void setSupplier(Supplier<Locale> supplier) {
        SUPPLIER = checkNotNull(supplier);
    }

    public static Locale get() {
        return SUPPLIER.get();
    }

    private LocaleProvider() {
    }

}
