package com.example.workintech.ecomm.exceptions;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends ECommerceException{
    public EntityNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
