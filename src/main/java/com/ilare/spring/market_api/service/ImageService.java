package com.ilare.spring.market_api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import com.ilare.spring.market_api.dto.ImageDTO;
import com.ilare.spring.market_api.entity.Image;
import com.ilare.spring.market_api.entity.Product;
import com.ilare.spring.market_api.exception.ImageNotFoundException;
import com.ilare.spring.market_api.exception.ProductNotFoundException;
import com.ilare.spring.market_api.repository.ImageRepository;
import com.ilare.spring.market_api.repository.ProductRepository;
import com.ilare.spring.market_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ImageRepository imgRepository;

    @Autowired
    UserRepository userRepository;
    private String IMG_DIR = "src/main/resources/photos/";
    public void addImage(MultipartFile file, Long productId) throws IOException, ProductNotFoundException {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

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

    public void deleteImage(Long imageId) throws IOException, ImageNotFoundException {
        Image image = imgRepository.findById(imageId).orElseThrow(() -> new ImageNotFoundException("Image not found"));

        Path path = Paths.get(image.getUrl());
        Files.delete(path);

        imgRepository.deleteById(imageId);
    }

    public ImageDTO getImage(Long imageId) throws ImageNotFoundException, IOException {
        Image image = imgRepository.findById(imageId).orElseThrow(() -> new ImageNotFoundException("Image not found"));

        Path path = Paths.get(image.getUrl());
        byte[] bytes = Files.readAllBytes(path);

        return new ImageDTO(image, bytes);
    }
}
