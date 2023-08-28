package com.noah.backend.commons.advice;

import com.noah.backend.commons.exception.MemberNotFoundException;
import com.noah.backend.commons.exception.UnAuthorizedAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.noah.backend.commons.HttpStatusResponseEntity.RESPONSE_FORBIDDEN;
import static com.noah.backend.commons.HttpStatusResponseEntity.RESPONSE_NOT_FOUND;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<HttpStatus> memberNotFoundException() {
        return RESPONSE_NOT_FOUND;
    }

    @ExceptionHandler(UnAuthorizedAccessException.class)
    public ResponseEntity<HttpStatus> unAuthorizedAccessException() {
        return RESPONSE_FORBIDDEN;
    }
}
