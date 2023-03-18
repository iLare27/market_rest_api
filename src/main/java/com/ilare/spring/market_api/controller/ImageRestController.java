package com.ilare.spring.market_api.controller;

import com.ilare.spring.market_api.dto.ImageDTO;
import com.ilare.spring.market_api.exception.ForbiddenException;
import com.ilare.spring.market_api.exception.ImageNotFoundException;
import com.ilare.spring.market_api.service.ImageService;
import com.ilare.spring.market_api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/images")
public class ImageRestController {

    @Autowired
    ImageService imageService;

    @Autowired
    ProductService productService;

    @GetMapping("/{imageId}")
    public ResponseEntity<ImageDTO> getImage(@PathVariable Long imageId) {
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
    public ResponseEntity<String> deleteImage(@PathVariable Long imageId, Principal principal) {
        try {
            imageService.deleteImage(imageId, principal);
            return ResponseEntity.ok("Image has been deleted");
        }
        catch(IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        catch(ImageNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        catch(ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
