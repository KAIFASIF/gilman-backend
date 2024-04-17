package com.kaif.gilmanbackend.exceptions;

public class DataNotSaved extends RuntimeException {
    public DataNotSaved(String message) {
        super(message);
    }
}