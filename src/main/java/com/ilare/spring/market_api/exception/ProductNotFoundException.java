package com.ilare.spring.market_api.exception;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String eMessage) {
        super(eMessage);
    }
}
