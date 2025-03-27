package com.clara.back.endpoints.rest.service.exceptions;

/**
 * @Autor Daniel Camilo
 */
public class InternalServiceException extends RuntimeException {

    /**
     *
     * @param message
     */
    public InternalServiceException(String message) {
        super(message);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public InternalServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}