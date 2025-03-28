package com.clara.back.endpoints.rest.service.exceptions;

import java.io.Serializable;

/**
 * @Autor Daniel Camilo
 */
public class DiscogsException extends RuntimeException implements Serializable {

    /**
     *
     * @param message
     */
    public DiscogsException(String message) {
        super(message);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public DiscogsException(String message, Throwable cause) {
        super(message, cause);
    }
}
