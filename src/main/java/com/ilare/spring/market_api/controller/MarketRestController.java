package com.ilare.spring.market_api.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MarketRestController {

    @GetMapping
    public String welcomeMsg(){
        return "Market API";
    }
}
