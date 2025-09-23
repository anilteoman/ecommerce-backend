package com.example.workintech.ecomm.exceptions;

import org.springframework.http.HttpStatus;

public class CardAlreadyExistsException extends ECommerceException{
    public CardAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
