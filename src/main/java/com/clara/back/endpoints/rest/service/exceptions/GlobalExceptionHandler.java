package com.clara.back.endpoints.rest.service.exceptions;

import com.clara.back.endpoints.rest.service.dto.ErrorDetailsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DiscogsException.class)
    public ResponseEntity<Object> handleNotFoundDataException(DiscogsException ex){
        ErrorDetailsDTO errorDetailsDTO = new ErrorDetailsDTO(HttpStatus.NO_CONTENT, HttpStatus.NO_CONTENT.value(), ex.getMessage());
        return ResponseEntity.status(errorDetailsDTO.getStatus()).body(errorDetailsDTO);
    }


    @ExceptionHandler(NoArgumentsException.class)
    public ResponseEntity<Object> handleNotFoundDataException(NoArgumentsException ex){
        ErrorDetailsDTO errorDetailsDTO = new ErrorDetailsDTO(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(errorDetailsDTO.getStatus()).body(errorDetailsDTO);
    }

    @ExceptionHandler(InternalServiceException.class)
    public ResponseEntity<Object> handleNotFoundDataException(InternalServiceException e){
        ErrorDetailsDTO errorDetailsDTO = new ErrorDetailsDTO(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), e.getCause());
        return ResponseEntity.status(errorDetailsDTO.getStatus()).body(errorDetailsDTO);
    }


}
