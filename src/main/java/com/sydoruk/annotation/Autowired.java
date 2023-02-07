package com.sydoruk.annotation;

import com.sydoruk.repository.InterfaceRepository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.CONSTRUCTOR)
public @interface Autowired {
    Class<? extends InterfaceRepository> classImplementation();
}

