package com.history.api.disney.exceptions;

public class BadRequestException extends RuntimeException{
    private static final String DESCRIPTION = "400";

    public BadRequestException(String message) {
        super(message);
    }

}
