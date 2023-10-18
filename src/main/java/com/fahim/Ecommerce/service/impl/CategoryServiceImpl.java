package com.fahim.Ecommerce.service.impl;

import com.fahim.Ecommerce.model.shop.Category;
import com.fahim.Ecommerce.model.shop.SubCategory;
import com.fahim.Ecommerce.repo.CategoryRepository;
import com.fahim.Ecommerce.repo.SubCategoryRepository;
import com.fahim.Ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;


    @Override
    public Category addCategory(Category category) {
        String randomId = UUID.randomUUID().toString();
        category.setCategoryId(randomId);
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getCategories() {
        List<Category> categoryList = categoryRepository.findAll();

        for(Category category : categoryList) {
            List<SubCategory> subCategoryList = subCategoryRepository.findByCategoryId(category.getCategoryId());
            category.setSubCategories(subCategoryList);
        }
        return categoryList;
    }


    @Override
    public Category getCategory(String categoryId) {

        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category != null) {
            List<SubCategory> byCategoryId = subCategoryRepository.findByCategoryId(categoryId);
            category.setSubCategories(byCategoryId);
        }
        return category;
    }

    @Override
    public void deleteCategory(String categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
