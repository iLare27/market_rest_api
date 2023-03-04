package com.ilare.spring.market_api.exception;

public class InvalidProductException extends Exception {
    public InvalidProductException(String eMessage) {
        super(eMessage);
    }
}
