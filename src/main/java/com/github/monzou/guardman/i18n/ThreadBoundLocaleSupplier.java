package com.github.monzou.guardman.i18n;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Locale;

import com.google.common.base.Supplier;

/**
 * Thread bound locale supplier for GuardMan
 * 
 * @see LocaleProvider
 * @see ResourceBundleRepository
 */
public class ThreadBoundLocaleSupplier implements Supplier<Locale> {

    private static final ThreadLocal<Locale> LOCALE = new ThreadLocal<Locale>() {
        @Override
        protected Locale initialValue() {
            return Locale.getDefault();
        }
    };

    /** {@inheritDoc} */
    @Override
    public Locale get() {
        return LOCALE.get();
    }

    public void set(Locale locale) {
        LOCALE.set(checkNotNull(locale));
    }

    public void remove() {
        LOCALE.remove();
    }

}
