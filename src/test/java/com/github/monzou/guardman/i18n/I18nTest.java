package com.github.monzou.guardman.i18n;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Locale;

import org.junit.Test;

import com.github.monzou.guardman.exception.ResourceNotFoundRuntimeException;
import com.github.monzou.guardman.exception.TranslationFailedRuntimeException;
import com.google.common.base.Suppliers;

/**
 * I18nTest
 */
public class I18nTest {

    @Test
    public void testTranslation() {

        I18n i18n = I18n.from(ResourceBundleRepository.get("messages"));

        LocaleProvider.setSupplier(Suppliers.ofInstance(Locale.ENGLISH));
        assertThat(i18n.translate("required", "Foo"), is("Foo is required."));

        LocaleProvider.setSupplier(Suppliers.ofInstance(Locale.JAPANESE));
        assertThat(i18n.translate("required", "Foo"), is("Foo は必須です。"));

        // Fallback to root locale
        LocaleProvider.setSupplier(Suppliers.ofInstance(Locale.FRENCH));
        assertThat(i18n.translate("required", "Foo"), is("Foo is required (default)."));

    }

    @Test(expected = TranslationFailedRuntimeException.class)
    public void testUnknownTranslation() {

        I18n i18n = I18n.from(ResourceBundleRepository.get("messages"));

        LocaleProvider.setSupplier(Suppliers.ofInstance(Locale.ENGLISH));
        i18n.translate("foo", "bar");

    }

    @Test(expected = ResourceNotFoundRuntimeException.class)
    public void testUnknownResource() {
        I18n i18n = I18n.from(ResourceBundleRepository.get("messages-not-found"));
        i18n.translate("required", "Foo");
    }

}
