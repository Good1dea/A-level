package com.sydoruk.util;

import com.sydoruk.annotation.Autowired;
import com.sydoruk.annotation.Singleton;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class AnnotationProcessor {

    private static final Map<Class<?>, Object> cash = new HashMap<>();

    public void initClassesWithSingleton() {
        final Reflections reflections = new Reflections("com.sydoruk.repository");
        final Set<Class<?>> classesWithSingleton = reflections.getTypesAnnotatedWith(Singleton.class);
        if (classesWithSingleton.isEmpty()) {
            System.out.println("There are no classes with @Singleton");
        } else {
            for (Class<?> classWithSingleton : classesWithSingleton) {
                try {
                    Method methodGetInstance = classWithSingleton.getMethod("getInstance");
                    Object init = methodGetInstance.invoke(classWithSingleton);
                    if (!cash.containsKey(classWithSingleton)) {
                        cash.put(classWithSingleton, init);
                    }
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void initClassesWithAutowired() {
        final Reflections reflections = new Reflections("com.sydoruk.service");
        final Set<Class<?>> classesWithSingleton = reflections.getTypesAnnotatedWith(Singleton.class);
        if (classesWithSingleton.isEmpty()) {
            System.out.println("There are no classes with @Singleton");
        } else {
            for (Class<?> classWithSingleton : classesWithSingleton) {
                final Constructor<?>[] constructors = classWithSingleton.getDeclaredConstructors();
                Arrays.stream(constructors)
                        .filter(constructor -> (constructor.getDeclaredAnnotation(Autowired.class)) != null)
                        .forEach(constructor -> {
                            constructor.setAccessible(true);
                            final Class repository = constructor.getDeclaredAnnotation(Autowired.class).classImplementation();
                            try {
                                Object init = constructor.newInstance(cash.get(repository));
                                if (!cash.containsKey(classWithSingleton)) {
                                    cash.put(classWithSingleton, init);
                                }
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        });
            }

        }
    }

    public void printCash() {
        System.out.println(cash);
    }
}