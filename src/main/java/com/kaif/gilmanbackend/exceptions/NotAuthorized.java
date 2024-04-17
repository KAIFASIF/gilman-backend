package com.kaif.gilmanbackend.exceptions;

public class NotAuthorized extends RuntimeException {
    public NotAuthorized(String message) {
        super(message);
    }
}