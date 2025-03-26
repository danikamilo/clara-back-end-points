package com.clara.back.endpoints.rest.service.exceptions;

import com.clara.back.endpoints.rest.service.dto.ErrorDetailsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DiscogsException.class)
    public ResponseEntity<Object> handleNotFoundDataException(DiscogsException ex){
        ErrorDetailsDTO errorDetailsDTO = new ErrorDetailsDTO(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex.getLocalizedMessage());
        return ResponseEntity.status(errorDetailsDTO.getStatus()).body(errorDetailsDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        ErrorDetailsDTO errorDetailsDTO = new ErrorDetailsDTO(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
