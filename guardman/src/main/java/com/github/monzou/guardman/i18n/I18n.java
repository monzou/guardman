package com.github.monzou.guardman.i18n;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.github.monzou.guardman.exception.TranslationFailedRuntimeException;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

/**
 * I18n messages for GuardMan
 * 
 * @see LocaleProvider
 * @see ResourceBundleRepository
 */
@SuppressWarnings("serial")
public final class I18n implements Serializable {

    public static I18n from(ResourceBundleRepository... repositories) {
        return new I18n(ImmutableList.copyOf(repositories));
    }

    private final List<ResourceBundleRepository> repositories;

    private I18n(List<ResourceBundleRepository> repositories) {
        this.repositories = repositories;
    }

    public String translate(String key, Object... params) {
        return translate(null, key, params);
    }

    public String translate(Locale locale, String key, Object... params) {
        String pattern = getPattern(locale, key);
        if (pattern != null) {
            return MessageFormat.format(pattern, params);
        }
        throw new TranslationFailedRuntimeException(String.format( //
                "Failed to localize message: key=%s,  args=%s, bundles=%s", //
                key, Joiner.on(", ").join(params), Joiner.on(", ").join(repositories)));
    }

    public String getPattern(String key) {
        return getPattern(null, key);
    }

    public String getPattern(Locale locale, String key) {
        Locale l = locale == null ? LocaleProvider.get() : locale;
        for (ResourceBundleRepository repository : repositories) {
            ResourceBundle bundle = repository.get(l);
            if (bundle != null) {
                try {
                    String pattern = bundle.getString(key);
                    if (pattern != null) {
                        return pattern;
                    }
                } catch (MissingResourceException ignore) { // SUPPRESS CHECKSTYLE
                }
            }
        }
        return null;
    }
}
