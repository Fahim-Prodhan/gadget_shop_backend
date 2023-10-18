package com.fahim.Ecommerce.repo;

import com.fahim.Ecommerce.model.shop.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,String > {
}
