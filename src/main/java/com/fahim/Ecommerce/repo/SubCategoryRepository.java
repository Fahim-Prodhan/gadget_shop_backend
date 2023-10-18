package com.fahim.Ecommerce.repo;

import com.fahim.Ecommerce.model.shop.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory, String > {
    List<SubCategory> findByCategoryId(String cId);
}
