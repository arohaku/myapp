package com.noah.backend.commons.advice.exception;

public class UnAuthorizedAccessException extends RuntimeException {

    public UnAuthorizedAccessException() {
        super();
    }

    public UnAuthorizedAccessException(String message) {
        super(message);
    }

    public UnAuthorizedAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}