package com.ilare.spring.market_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Image> images;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    //добавить переменную - категория товара
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
