package com.github.monzou.guardman.i18n;

/**
 * GuardMan messages
 * 
 * @see I18n
 */
public final class Messages {

    private static final I18n I18N = I18n.from(ResourceBundleRepository.get("guardman"));

    public static String get(String key, Object... args) {
        return I18N.translate(key, args);
    }

    private Messages() {
    }

}
