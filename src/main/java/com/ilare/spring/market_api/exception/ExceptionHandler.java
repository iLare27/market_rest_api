package com.ilare.spring.market_api.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.postgresql.util.PSQLException;

import java.util.HashMap;
import java.util.Map;

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

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        BindingResult bindingResult = e.getBindingResult();

        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(errors);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtTokenException(ExpiredJwtException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token has expired, log in");
    }
}