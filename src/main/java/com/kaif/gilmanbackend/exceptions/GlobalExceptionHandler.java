package com.kaif.gilmanbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<String> handleResouceNotFoundException(ResourceNotFound ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataNotSaved.class)
    public ResponseEntity<String> handleDataNotSavedException(DataNotSaved ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(NotAuthorized.class)
    public ResponseEntity<String> handleNotAuthorizedException(DataNotSaved ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

   

}