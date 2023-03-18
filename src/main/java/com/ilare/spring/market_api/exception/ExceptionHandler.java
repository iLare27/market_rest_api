package com.ilare.spring.market_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.postgresql.util.PSQLException;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(PSQLException.class)
    public ResponseEntity<String> handlePSQLException(PSQLException e) {
        String message = e.getMessage();

        if (message.contains("unique_email")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
        }
        else if (message.contains("unique_nickname")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nickname already in use");
        }
        else if (message.contains("unique_phonenumber")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Phone number already in use");
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }
}