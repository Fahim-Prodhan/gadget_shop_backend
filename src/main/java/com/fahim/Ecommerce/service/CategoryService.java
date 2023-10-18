package com.fahim.Ecommerce.service;

import com.fahim.Ecommerce.model.shop.Category;
import com.fahim.Ecommerce.model.shop.SubCategory;

import java.util.List;


public interface CategoryService {
    Category addCategory(Category category);
    Category updateCategory(Category category);
    List<Category> getCategories();
    Category getCategory(String categoryId);
    void deleteCategory(String categoryId);

}
