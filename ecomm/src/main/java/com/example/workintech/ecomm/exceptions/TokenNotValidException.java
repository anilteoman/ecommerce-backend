package com.example.workintech.ecomm.exceptions;

import org.springframework.http.HttpStatus;

public class TokenNotValidException extends ECommerceException{
    public TokenNotValidException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
