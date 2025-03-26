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
    private String message;
    private String details;

    public ErrorDetailsDTO(HttpStatus status, String message, String details) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.details = details;
    }
}
