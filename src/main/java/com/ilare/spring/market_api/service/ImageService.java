package com.ilare.spring.market_api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.UUID;

import com.ilare.spring.market_api.dto.ImageDTO;
import com.ilare.spring.market_api.entity.Image;
import com.ilare.spring.market_api.entity.Product;
import com.ilare.spring.market_api.entity.User;
import com.ilare.spring.market_api.exception.ForbiddenException;
import com.ilare.spring.market_api.exception.ImageNotFoundException;
import com.ilare.spring.market_api.exception.ProductNotFoundException;
import com.ilare.spring.market_api.repository.ImageRepository;
import com.ilare.spring.market_api.repository.ProductRepository;
import com.ilare.spring.market_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService { //TODO add image validation

    private final ProductRepository productRepository;

    private final ImageRepository imgRepository;

    @Autowired
    UserRepository userRepository;

    private String IMG_DIR = "src/main/resources/photos/";

    public void addImage(MultipartFile file, Long productId, Principal principal) throws IOException, ProductNotFoundException, ForbiddenException {

        User user = userRepository.findByEmail(principal.getName());

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (user.getId() == product.getUserId()) {
            String url = IMG_DIR + product.getUserId() + "_" + productId + "_" + UUID.randomUUID() + ".png";
            byte[] bytes = file.getBytes();
            Path path = Paths.get(url);
            Files.write(path, bytes);

            Image image = new Image();
            image.setUrl(url);

            product.addImageToProduct(image);

            imgRepository.save(image);
            productRepository.save(product);
        }
        else {
            throw new ForbiddenException("User must have permission");
        }
    }

    public void deleteImage(Long imageId, Principal principal) throws IOException, ImageNotFoundException, ForbiddenException {

        User user = userRepository.findByEmail(principal.getName());

        Image image = imgRepository.findById(imageId).orElseThrow(() -> new ImageNotFoundException("Image not found"));

        if (user.getId() == image.getProduct().getUserId()) {
            Path path = Paths.get(image.getUrl());
            Files.delete(path);

            imgRepository.deleteById(imageId);
        }
        else {
            throw new ForbiddenException("User must have permission");
        }
    }

    public ImageDTO getImage(Long imageId) throws ImageNotFoundException, IOException {
        Image image = imgRepository.findById(imageId).orElseThrow(() -> new ImageNotFoundException("Image not found"));

        Path path = Paths.get(image.getUrl());
        byte[] bytes = Files.readAllBytes(path);

        return new ImageDTO(image, bytes);
    }
}
