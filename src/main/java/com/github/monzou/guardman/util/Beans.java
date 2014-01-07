package com.github.monzou.guardman.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import com.github.monzou.guardman.exception.GuardManRuntimeException;
import com.github.monzou.guardman.exception.PropertyAccessRuntimeException;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

/**
 * Beans
 */
public final class Beans {

    private static Cache<Class<?>, Map<String, PropertyDescriptor>> CACHE;
    static {
        CACHE = CacheBuilder.newBuilder().concurrencyLevel(Runtime.getRuntime().availableProcessors()).weakKeys().build();
    }

    public static Object getProperty(Object bean, String propertyName) {
        Class<?> clazz = bean.getClass();
        PropertyDescriptor descriptor = getPropertyDescriptor(clazz, propertyName);
        Method method = descriptor.getReadMethod();
        if (method == null) {
            String message = String.format("ReadMethod not found: class=%s, property=%s", clazz.getName(), propertyName);
            throw new PropertyAccessRuntimeException(message);
        }
        return Reflections.invoke(bean, method);
    }

    public static void setProperty(Object bean, String propertyName, Object value) {
        Class<?> clazz = bean.getClass();
        PropertyDescriptor descriptor = getPropertyDescriptor(clazz, propertyName);
        Method method = descriptor.getWriteMethod();
        if (method == null) {
            String message = String.format("WriteMethod not found: class=%s, property=%s", clazz.getName(), propertyName);
            throw new PropertyAccessRuntimeException(message);
        }
        Reflections.invoke(bean, method, new Object[] { value });
    }
    
    private static PropertyDescriptor getPropertyDescriptor(Class<?> clazz, String propertyName) {
        PropertyDescriptor descriptor = getPropertyDescriptors(clazz).get(propertyName);
        if (descriptor == null) {
            String message = String.format("Property not found: class=%s, property=%s", clazz.getName(), propertyName);
            throw new PropertyAccessRuntimeException(message);
        }
        return descriptor;
    }

    private static Map<String, PropertyDescriptor> getPropertyDescriptors(final Class<?> clazz) {
        try {
            return CACHE.get(clazz, new Callable<Map<String, PropertyDescriptor>>() {
                @Override
                public Map<String, PropertyDescriptor> call() throws Exception { // SUPPRESS CHECKSTYLE
                    List<BeanInfo> beanInfoList = collectBeanInfo(clazz);
                    Map<String, PropertyDescriptor> descriptors = Maps.newHashMap();
                    for (BeanInfo beanInfo : beanInfoList) {
                        for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                            PropertyDescriptor existence = descriptors.get(propertyDescriptor.getName());
                            Class<?> existenceType = existence == null ? null : existence.getPropertyType();
                            if (existenceType == null || !propertyDescriptor.getPropertyType().isAssignableFrom(existenceType)) {
                                descriptors.put(propertyDescriptor.getName(), propertyDescriptor);
                            }
                        }
                    }
                    return ImmutableMap.copyOf(descriptors);
                }
            });
        } catch (ExecutionException e) {
            throw new GuardManRuntimeException(e);
        }
    }

    private static List<BeanInfo> collectBeanInfo(Class<?> clazz) {
        Builder<BeanInfo> builder = ImmutableList.builder();
        BeanInfo info = getBeanInfo(clazz);
        if (info != null) {
            builder.add(info);
        }
        for (Class<?> i : clazz.getInterfaces()) {
            if (Modifier.isPublic(i.getModifiers())) {
                builder.addAll(collectBeanInfo(i));
            }
        }
        return builder.build();
    }

    private static BeanInfo getBeanInfo(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        try {
            return Modifier.isPublic(clazz.getModifiers()) ? Introspector.getBeanInfo(clazz) : getBeanInfo(clazz.getSuperclass());
        } catch (IntrospectionException e) {
            throw new GuardManRuntimeException(e);
        }
    }

    private Beans() {
    }

}
