package com.sydoruk;

import com.sydoruk.util.AnnotationProcessor;

public class Main {

    public static void main(final String[] args) {

        AnnotationProcessor annotationProcessor = new AnnotationProcessor();
        annotationProcessor.initClassesWithSingleton();
        annotationProcessor.initClassesWithAutowired();
        annotationProcessor.printCash();
    }
}