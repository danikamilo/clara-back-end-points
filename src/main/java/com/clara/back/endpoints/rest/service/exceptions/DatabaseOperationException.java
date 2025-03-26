package com.clara.back.endpoints.rest.service.exceptions;

public class DatabaseOperationException extends RuntimeException {

    public DatabaseOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}