package com.ilare.spring.market_api.controller;

import com.ilare.spring.market_api.entity.Product;
import com.ilare.spring.market_api.exception.ForbiddenException;
import com.ilare.spring.market_api.exception.InvalidProductException;
import com.ilare.spring.market_api.exception.ProductNotFoundException;
import com.ilare.spring.market_api.service.ImageService;
import com.ilare.spring.market_api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductRestController { //TODO do User verification (role, id)

    private final ProductService productService;

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product, Principal principal) {
        try {
            productService.saveProduct(product, principal);
            return ResponseEntity.ok(product);
        }
        catch(InvalidProductException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, Principal principal) {
        try {
            productService.saveProduct(product, principal);
            return ResponseEntity.ok(product);
        }
        catch(InvalidProductException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Long productId) {
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
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productService.getProducts();
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId, Principal principal) {
        try {
            productService.deleteProduct(productId, principal);
            return ResponseEntity.ok("Product has been deleted");
        }
        catch(ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        catch(ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/{productId}")
    public ResponseEntity<String> addImageToProduct(
            @RequestParam("image") MultipartFile multipartFile,
            @PathVariable Long productId,
            Principal principal
    ) {
        try {
            imageService.addImage(multipartFile, productId, principal);
            return ResponseEntity.ok("Image has been added");
        }
        catch(IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        catch(ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        catch(ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
