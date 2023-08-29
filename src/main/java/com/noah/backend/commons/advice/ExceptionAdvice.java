package com.noah.backend.commons.advice;

import com.noah.backend.commons.advice.exception.CategoryNotFoundException;
import com.noah.backend.commons.advice.exception.MemberNotFoundException;
import com.noah.backend.commons.advice.exception.UnAuthorizedAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> validationNotValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(e.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<String> categoryNotFoundException(CategoryNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
