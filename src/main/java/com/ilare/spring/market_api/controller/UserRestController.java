package com.ilare.spring.market_api.controller;

import com.ilare.spring.market_api.entity.Product;
import com.ilare.spring.market_api.entity.User;
import com.ilare.spring.market_api.exception.UserNotFoundException;
import com.ilare.spring.market_api.service.ProductService;
import com.ilare.spring.market_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {;
        try {
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        }
        catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<?> getUsers() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok().build();
        }
        catch(UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

//    @PutMapping
//    public void updateUser(@RequestBody User user) {
//        userService.
//    }

    @PostMapping(value = "/{userId}")
    public ResponseEntity<?> addProductToUser(@RequestBody Product product, @PathVariable Long userId) {
        try {
            productService.addProduct(product, userId);
            return ResponseEntity.ok().build();
        }
        catch(UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUserProduct(@RequestBody Product product, @PathVariable Long userId) {
        try {
            productService.updateProduct(product, userId);
            return ResponseEntity.ok().build();
        }
        catch(UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

