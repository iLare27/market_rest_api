package com.ilare.spring.market_api.service;

import com.ilare.spring.market_api.entity.Product;
import com.ilare.spring.market_api.entity.User;
import com.ilare.spring.market_api.exception.ProductNotFoundException;
import com.ilare.spring.market_api.exception.UserNotFoundException;
import com.ilare.spring.market_api.repository.ProductRepository;
import com.ilare.spring.market_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    public void addUser(User user) {
        userRepository.save(user); // требуется валидация данных
    }

    public void deleteUser(Long id) throws UserNotFoundException, ProductNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        for (Product product : user.getProducts()) {
            productService.deleteProduct(product.getId());
        }
        userRepository.deleteById(id);
    }

    public User getUserById(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        return user;
    }

    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

}
