package com.ilare.spring.market_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Column(name = "price")
    @Min(value = 99, message = "Price must be at least 100")
    private Double price;

    @NotBlank(message = "Description cannot be blank")
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Image> images;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public void addImageToProduct(Image ... images) {
        for (Image image: images) {
            image.setProduct(this);
            this.images.add(image);
        }
    }

}
