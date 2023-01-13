package com.ilare.spring.market_api.controller;

import com.ilare.spring.market_api.entity.Product;
import com.ilare.spring.market_api.exception.ProductNotFoundException;
import com.ilare.spring.market_api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    @Autowired
    ProductService productService;

    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable Long productId) throws ProductNotFoundException {
        Product product = productService.getProductById(productId);
        return product;
    }

    @GetMapping
    public List<Product> getProducts() {
        List<Product> products = productService.getProducts();
        return products;
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Long productId) throws ProductNotFoundException {
        productService.deleteProduct(productId);
    }
}
