package com.fahim.Ecommerce.model.shop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    private String subCategoryId;
    private String categoryId;
    private String productName;
    @Column(length = 50000)
    private String shortDescription;
    @Column(length = 50000)
    private String longDescription;
    private Double regularPrice;
    private Double discountPrice;
    private Long quantity;
    private boolean isAvailable;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "product_images",
            joinColumns = {
            @JoinColumn(name = "product_id")
            },
            inverseJoinColumns = {
            @JoinColumn(name = "image_id")
            }
    )
    private Set<Image> productImages;

}
