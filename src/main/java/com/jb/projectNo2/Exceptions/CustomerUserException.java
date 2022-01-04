package com.jb.projectNo2.Exceptions;

public class CustomerUserException extends Exception {
    public CustomerUserException() {
    }
    public CustomerUserException(String message) {
        super((message));
    }
}
