package com.example.workintech.ecomm.exceptions;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends ECommerceException{
    public EmailAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
