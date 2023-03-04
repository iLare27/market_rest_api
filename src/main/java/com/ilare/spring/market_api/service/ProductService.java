package com.ilare.spring.market_api.service;

import com.ilare.spring.market_api.entity.Product;
import com.ilare.spring.market_api.entity.User;
import com.ilare.spring.market_api.exception.ProductNotFoundException;
import com.ilare.spring.market_api.exception.UserNotFoundException;
import com.ilare.spring.market_api.repository.ProductRepository;
import com.ilare.spring.market_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageService imageService;

    public void addProduct(Product product, Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.addProductToUser(product);

        productRepository.save(product);
        userRepository.save(user);
    }

    public void updateProduct(Product product, Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.addProductToUser(product);

        productRepository.save(product);
        userRepository.save(user);
    }
    public void deleteProduct(Long productId) throws ProductNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        productRepository.delete(product);
    }

    public Product getProductById(Long productId) throws ProductNotFoundException, IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return product;
    }

    public List<Product> getProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }

//    private void deletePhoto(String photoPath) {
//        File file = new File(photoPath);
//        if (file.exists()) {
//            file.delete();
//        }
//    }
}
