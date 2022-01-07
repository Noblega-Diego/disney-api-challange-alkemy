package com.history.api.disney.exceptions;

public class NotFoundException extends RuntimeException {
    private static final String DESCRIPTION = "404";

    public NotFoundException(String message) {
        super(message);
    }
}
