package com.ilare.spring.market_api.controller;

import com.ilare.spring.market_api.dto.ImageDTO;
import com.ilare.spring.market_api.exception.ImageNotFoundException;
import com.ilare.spring.market_api.service.ImageService;
import com.ilare.spring.market_api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class ImageRestController {

    @Autowired
    ImageService imageService;

    @Autowired
    ProductService productService;

    @GetMapping("/{imageId}")
    public ResponseEntity<?> getImage(@PathVariable Long imageId) {
        try {
            ImageDTO imageDTO = imageService.getImage(imageId);
            return ResponseEntity.ok(imageDTO);
        }
        catch(IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        catch(ImageNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable Long imageId) {
        try {
            imageService.deleteImage(imageId);
            return ResponseEntity.ok().build();
        }
        catch(IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        catch(ImageNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
