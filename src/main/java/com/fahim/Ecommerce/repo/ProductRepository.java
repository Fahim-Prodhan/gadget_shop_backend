package com.fahim.Ecommerce.repo;

import com.fahim.Ecommerce.model.shop.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long > {
        List<Product> findBySubCategoryId(String subId);
        List<Product> findProductByCategoryId(String catId);
        List<Product> findAllByProductNameContainingIgnoreCaseOrShortDescriptionContainsIgnoreCase(
                String key1,String key2,Pageable pageable
        );

}
