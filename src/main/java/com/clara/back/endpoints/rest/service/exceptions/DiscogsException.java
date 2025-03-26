package com.clara.back.endpoints.rest.service.exceptions;

import java.io.Serializable;

public class DiscogsException extends RuntimeException implements Serializable {

    public DiscogsException(String message) {
        super(message);
    }

    public DiscogsException(String message, Throwable cause) {
        super(message, cause);
    }
}
