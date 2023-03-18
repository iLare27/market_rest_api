package com.ilare.spring.market_api.exception;

public class ForbiddenException extends Exception {
    public ForbiddenException(String eMessage) {
        super(eMessage);
    }
}
