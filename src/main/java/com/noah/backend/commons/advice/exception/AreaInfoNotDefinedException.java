package com.noah.backend.commons.advice.exception;

public class AreaInfoNotDefinedException extends RuntimeException {

    public AreaInfoNotDefinedException() {
        super();
    }

    public AreaInfoNotDefinedException(String message) {
        super(message);
    }

    public AreaInfoNotDefinedException(String message, Throwable cause) {
        super(message, cause);
    }
}