package com.history.api.disney.exceptions;

public class UnathiorizedException extends RuntimeException {
    private static final String DESCRIPTION = "401";

    public UnathiorizedException(String message) {
        super(message);
    }
}
