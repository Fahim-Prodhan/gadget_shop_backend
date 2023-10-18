package com.fahim.Ecommerce.model.shop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class SubCategory {
    @Id
    private String subCategoryId;
    private String categoryId;
    private String name;

    @Transient
    private List<Product> products = new ArrayList<>();
}
