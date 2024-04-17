package com.kaif.gilmanbackend.exceptions;

public class DataNotSavedException extends RuntimeException {
    public DataNotSavedException(String message) {
        super(message);
    }
}