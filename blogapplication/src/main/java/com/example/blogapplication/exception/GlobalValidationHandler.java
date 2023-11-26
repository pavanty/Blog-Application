package com.example.blogapplication.exception;

import com.example.blogapplication.exception.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

public class GlobalValidationHandler extends ResponseEntityExceptionHandler {
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleResourseNotFoundException(UserNotFoundException exec) {

        return new ResponseEntity<String>(exec.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogCategoryNotFoundException.class)
    public ResponseEntity<String> handleResourseNotFoundException(BlogCategoryNotFoundException exec) {

        return new ResponseEntity<String>(exec.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogNotFoundException.class)
    public ResponseEntity<String> handleResourseNotFoundException(BlogNotFoundException exec) {

        return new ResponseEntity<String>(exec.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<String> handleResourseNotFoundException(CommentNotFoundException exec) {

        return new ResponseEntity<String>(exec.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BlogActionLikedException.class)
    public ResponseEntity<String> handleResourseNotFoundException(BlogActionLikedException exec) {

        return new ResponseEntity<String>(exec.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BlogExistsExpection.class)
    public ResponseEntity<String> handleResourseNotFoundException(BlogExistsExpection exec) {

        return new ResponseEntity<String>(exec.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BlogCategoryBlogpresentException.class)
    public ResponseEntity<String> handleResourseNotFoundException(BlogCategoryBlogpresentException exec) {

        return new ResponseEntity<String>(exec.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }
}
