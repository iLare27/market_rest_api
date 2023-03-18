package com.ilare.spring.market_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MarketRestController {

    @GetMapping
    public ResponseEntity<String> welcomeMsg(){
        return ResponseEntity.ok("made by iLare");
    }
}
