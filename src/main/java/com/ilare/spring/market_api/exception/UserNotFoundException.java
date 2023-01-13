package com.ilare.spring.market_api.exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException (String eMessage) {
        super(eMessage);
    }
}
