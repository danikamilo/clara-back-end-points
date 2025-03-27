package com.clara.back.endpoints.rest.service.exceptions;

/**
 * @Autor Daniel Camilo
 */
public class NoArgumentsException extends RuntimeException {

    /**
     *
     * @param message
     */
    public NoArgumentsException(String message) {
        super(message);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public NoArgumentsException(String message, Throwable cause) {
        super(message, cause);
    }
}
