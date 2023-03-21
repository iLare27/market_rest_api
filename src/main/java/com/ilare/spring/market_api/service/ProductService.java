package com.ilare.spring.market_api.service;

import com.ilare.spring.market_api.entity.Product;
import com.ilare.spring.market_api.entity.User;
import com.ilare.spring.market_api.exception.ForbiddenException;
import com.ilare.spring.market_api.exception.InvalidProductException;
import com.ilare.spring.market_api.exception.ProductNotFoundException;
import com.ilare.spring.market_api.repository.ProductRepository;
import com.ilare.spring.market_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    public void saveProduct(Product product, Principal principal) throws InvalidProductException {
        User user = userRepository.findByEmail(principal.getName());

        user.addProductToUser(product);

        productRepository.save(product);
        userRepository.save(user);
    }

    public void deleteProduct(Long productId, Principal principal) throws ProductNotFoundException, ForbiddenException {

        User user = userRepository.findByEmail(principal.getName());

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (user.getId() == product.getUserId()) {
            productRepository.delete(product);
        }
        else {
            throw new ForbiddenException("User must have permission");
        }
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
}
