package com.github.monzou.guardman.i18n;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

import com.github.monzou.guardman.exception.GuardManRuntimeException;
import com.github.monzou.guardman.exception.ResourceNotFoundRuntimeException;
import com.google.common.base.Objects;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import com.google.common.io.Closer;

/**
 * ResourceBundleRespository for GuardMan
 *
 * @see LocaleProvider
 */
@SuppressWarnings("serial")
public class ResourceBundleRepository implements Serializable {

    private static final String CHARSET_NAME = System.getProperty("guardman.charset", "UTF-8");

    private static final Locale FALLBACK_LOCALE = Locale.ROOT;

    private static final Control CONTROL = new ResourceBundleControl();

    private static final Cache<String, ResourceBundleRepository> CACHE;
    static {
        CACHE = CacheBuilder.newBuilder().concurrencyLevel(Runtime.getRuntime().availableProcessors()).weakValues().build();
    }

    public static ResourceBundleRepository get(Class<?> clazz) {
        return get(clazz.getName());
    }

    public static ResourceBundleRepository get(Package pkg, String baseName) {
        return get(String.format("%s.%s", pkg.getName(), baseName));
    }

    public static ResourceBundleRepository get(final String baseName) {
        try {
            return CACHE.get(baseName, new Callable<ResourceBundleRepository>() {
                @Override
                public ResourceBundleRepository call() throws Exception { // SUPPRESS CHECKSTYLE
                    return new ResourceBundleRepository(baseName);
                }
            });
        } catch (ExecutionException e) {
            throw new GuardManRuntimeException(e);
        }
    }

    private final String baseName;

    private final transient ConcurrentMap<Locale, ResourceBundle> bundles;

    private ResourceBundleRepository(String baseName) {
        this.baseName = baseName;
        this.bundles = Maps.newConcurrentMap();
    }

    ResourceBundle get(Locale locale) {
        ResourceBundle bundle = bundles.get(locale);
        if (bundle != null) {
            return bundle;
        }
        try {
            bundle = ResourceBundle.getBundle(baseName, locale, CONTROL);
            ResourceBundle existence = bundles.putIfAbsent(locale, bundle);
            if (existence != null) {
                bundle = existence;
            }
        } catch (MissingResourceException e) {
            String message = String.format("Failed to load resource: baseName=%s, locale=%s", baseName, locale);
            throw new ResourceNotFoundRuntimeException(message, e);
        }
        return bundle;
    }

    private static class ResourceBundleControl extends Control {

        /** {@inheritDoc} */
        @Override
        public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
                throws IllegalAccessException, InstantiationException, IOException {

            if ("java.properties".equals(format)) {
                String bundleName = toBundleName(baseName, locale);
                String resourceName = toResourceName(bundleName, "properties");
                Closer closer = Closer.create();
                try {
                    InputStream stream = closer.register(openStream(loader, resourceName, reload));
                    if (stream != null) {
                        Reader reader = closer.register(new BufferedReader(new InputStreamReader(stream, CHARSET_NAME)));
                        return new PropertyResourceBundle(reader);
                    }
                } catch (Throwable t) {
                    closer.rethrow(t);
                } finally {
                    closer.close();
                }
            }

            return super.newBundle(baseName, locale, format, loader, reload);

        }

        private InputStream openStream(final ClassLoader classLoader, final String resourceName, final boolean reload) throws IOException {
            try {
                return AccessController.doPrivileged(new PrivilegedExceptionAction<InputStream>() {
                    @Override
                    public InputStream run() throws IOException {
                        InputStream in = null;
                        if (reload) {
                            URL url = classLoader.getResource(resourceName);
                            if (url != null) {
                                URLConnection connection = url.openConnection();
                                if (connection != null) {
                                    connection.setUseCaches(false);
                                    in = connection.getInputStream();
                                }
                            }
                        } else {
                            in = classLoader.getResourceAsStream(resourceName);
                        }
                        return in;
                    }
                });
            } catch (PrivilegedActionException e) {
                throw (IOException) e.getException();
            }
        }

        /** {@inheritDoc} */
        @Override
        public List<String> getFormats(String baseName) {
            return ResourceBundle.Control.FORMAT_DEFAULT;
        }

        /** {@inheritDoc} */
        @Override
        public Locale getFallbackLocale(String baseName, Locale locale) {
            return Objects.equal(locale, FALLBACK_LOCALE) ? null : FALLBACK_LOCALE;
        }

    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return String.format("ResourceBundleRepository (%s)", baseName);
    }

    private Object readResolve() {
        return get(baseName);
    }

}
