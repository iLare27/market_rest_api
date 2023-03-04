package com.ilare.spring.market_api.exception;

public class InvalidUserException extends Exception {
    public InvalidUserException(String eMessage) {
        super(eMessage);
    }
}
