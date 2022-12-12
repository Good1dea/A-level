package com.sydoruk.exception;

public class UserInputException extends RuntimeException {

    public UserInputException () {
        super();
        System.out.println("UserInputException");
    }
}
