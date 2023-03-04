package com.ilare.spring.market_api.controller;

import com.ilare.spring.market_api.entity.Product;
import com.ilare.spring.market_api.exception.ProductNotFoundException;
import com.ilare.spring.market_api.service.ImageService;
import com.ilare.spring.market_api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    @Autowired
    ProductService productService;

    @Autowired
    ImageService imageService;

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable Long productId) {
        try {
            Product product = productService.getProductById(productId);
            return ResponseEntity.ok(product);
        }
        catch(ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        catch(IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<?> getProducts() {
        List<Product> products = productService.getProducts();
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok().build();
        }
        catch(ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/{productId}")
    public ResponseEntity<?> addImageToProduct(@RequestParam("image") MultipartFile multipartFile, @PathVariable Long productId) {
        try {
            imageService.addImage(multipartFile, productId);
            return ResponseEntity.ok().build();
        }
        catch(IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        catch(ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
