package com.example.workintech.ecomm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ECommerceErrorResponse> handleException(ECommerceException eCommerceException) {
        ECommerceErrorResponse response = new ECommerceErrorResponse(eCommerceException.getMessage(), eCommerceException.getHttpStatus().value(), System.currentTimeMillis(), LocalDateTime.now());

        return new ResponseEntity<>(response, eCommerceException.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ECommerceErrorResponse> handleException(Exception exception) {
        ECommerceErrorResponse response = new ECommerceErrorResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), System.currentTimeMillis(), LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
