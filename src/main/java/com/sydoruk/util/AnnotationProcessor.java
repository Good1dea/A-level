package com.sydoruk.util;

import com.sydoruk.annotation.Autowired;
import com.sydoruk.annotation.Singleton;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;

public class AnnotationProcessor {

    private final HashMap<Class<?>, Object> cash = new HashMap<>();

    public void findAndInitClassWithSingleton() {
        Reflections reflections = new Reflections("com.sydoruk.repository");
        final Set<Class<?>> classesWithSingleton = reflections.getTypesAnnotatedWith(Singleton.class);
        if (classesWithSingleton.isEmpty()) {
            System.out.println("There are no classes with @Singleton");
        } else {
            for (Class<?> classWithSingleton : classesWithSingleton) {
                try {
                    Method methodGetInstance = classWithSingleton.getMethod("getInstance");
                    Object init = methodGetInstance.invoke(classWithSingleton);
                    cash.put(classWithSingleton, init);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void findAndInitClassWithAutowired() {
        Reflections reflections = new Reflections("com.sydoruk.service");
        final Set<Constructor> constructorsWithAutowired = reflections.getConstructorsAnnotatedWith(Autowired.class);
        if (constructorsWithAutowired.isEmpty()) {
            System.out.println("There are no constructors with @Autowired");
        } else {
            constructorsWithAutowired.stream()
                    .peek(constructor -> constructor.setAccessible(true))
                    .forEach(constructor -> {
                        Class<?> argument = constructor.getDeclaredAnnotation(Autowired.class).classImplementation();
                        try {
                            Object init = constructor.newInstance(argument);
                            if (!cash.containsValue(init)) {
                                cash.put(init.getClass(), init);
                            }
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
    }

    public void printCash() {
        System.out.println(cash);
    }
}