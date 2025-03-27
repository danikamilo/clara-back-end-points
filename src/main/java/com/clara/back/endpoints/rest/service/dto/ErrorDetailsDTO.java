package com.clara.back.endpoints.rest.service.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * @Autor Daniel Camilo
 */
@Data
public class ErrorDetailsDTO {

    private LocalDateTime timestamp;
    private HttpStatus status;
    private Integer statusCode;
    private String message;
    private Throwable details;

    /**
     *
     * @param status
     * @param statusCode
     * @param message
     */
    public ErrorDetailsDTO(HttpStatus status, Integer statusCode, String message) {
        this.timestamp = LocalDateTime.now();
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;

    }

    /**
     *
     * @param status
     * @param statusCode
     * @param message
     * @param details
     */
    public ErrorDetailsDTO(HttpStatus status, Integer statusCode, String message, Throwable details) {
        this.timestamp = LocalDateTime.now();
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        this.details = details;
    }


}
