package com.ilare.spring.market_api.controller;

import com.ilare.spring.market_api.entity.Product;
import com.ilare.spring.market_api.entity.User;
import com.ilare.spring.market_api.exception.ProductNotFoundException;
import com.ilare.spring.market_api.exception.UserNotFoundException;
import com.ilare.spring.market_api.service.ProductService;
import com.ilare.spring.market_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable Long userId) throws UserNotFoundException {
        User user = userService.getUserById(userId);
        return user;
    }

    @GetMapping
    public List<User> getUsers() {
        List<User> users = userService.getUsers();
        return users;
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) throws UserNotFoundException, ProductNotFoundException {
        userService.deleteUser(userId);
    }

    //@PutMapping
    //public void updateUser(@RequestBody User user) {}

    @PostMapping("/{userId}")
    public void addProductToUser(@RequestBody Product product, @PathVariable Long userId) throws UserNotFoundException {
        productService.saveProduct(product, userId);
    }

    @PutMapping("/{userId}")
    public void updateUserProduct(@RequestBody Product product, @PathVariable Long userId) throws UserNotFoundException {
        productService.updateProduct(product, userId);
    }
}
