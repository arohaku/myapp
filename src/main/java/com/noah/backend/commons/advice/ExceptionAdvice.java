package com.noah.backend.commons.advice;

import com.noah.backend.member.exception.MemberNotFoundException;
import com.noah.backend.member.exception.PasswordNotMatchedException;
import com.noah.backend.member.exception.UnAuthenticatedAccessException;
import com.noah.backend.member.exception.UnAuthorizedAccessException;
import com.noah.backend.post.exception.AreaInfoNotDefinedException;
import com.noah.backend.post.exception.CategoryNotFoundException;
import com.noah.backend.post.exception.PostNotFoundException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.noah.backend.commons.HttpStatusResponseEntity.*;

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
    @ExceptionHandler(AreaInfoNotDefinedException.class)
    public ResponseEntity<String> areaInfoNotDefinedException(AreaInfoNotDefinedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<HttpStatus> postNotFoundException() {
        return RESPONSE_NOT_FOUND;
    }

    @ExceptionHandler(UnAuthenticatedAccessException.class)
    public ResponseEntity<HttpStatus> unAuthenticatedAccessException() {
        return RESPONSE_UNAUTHORIZED;
    }

    @ExceptionHandler(PasswordNotMatchedException.class)
    public ResponseEntity<HttpStatus> passwordNotMatchedException() {
        return RESPONSE_BAD_REQUEST;
    }

    @ExceptionHandler(FileSizeLimitExceededException.class)
    public ResponseEntity<HttpStatus> fileSizeLimitExceededException() {
        return RESPONSE_PAYLOAD_TOO_LARGE;
    }
}
