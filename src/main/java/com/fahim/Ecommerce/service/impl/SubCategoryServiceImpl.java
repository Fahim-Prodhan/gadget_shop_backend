package com.fahim.Ecommerce.service.impl;

import com.fahim.Ecommerce.model.shop.Product;
import com.fahim.Ecommerce.model.shop.SubCategory;
import com.fahim.Ecommerce.repo.ProductRepository;
import com.fahim.Ecommerce.repo.SubCategoryRepository;
import com.fahim.Ecommerce.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final ProductRepository productRepository;


    @Override
    public SubCategory addSubCategory(SubCategory subCategory) {
        String s = UUID.randomUUID().toString();
        subCategory.setSubCategoryId(s);
        return subCategoryRepository.save(subCategory);
    }

    @Override
    public SubCategory updateSubCategory(SubCategory subCategory) {
        return subCategoryRepository.save(subCategory);
    }

    @Override
    public SubCategory getSingleSubCategory(String sId) {
        SubCategory subCategory = subCategoryRepository.findById(sId).get();
        List<Product> productList = productRepository.findBySubCategoryId(sId);
        subCategory.setProducts(productList);
        return subCategory;
    }

    @Override
    public List<SubCategory> getAllSubCategory() {
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        for (SubCategory subCategory : subCategoryList) {
            List<Product> productList = productRepository.findBySubCategoryId(subCategory.getSubCategoryId());
            subCategory.setProducts(productList);
        }
        return subCategoryList;
    }

    @Override
    public void deleteSubCategory(String sId) {
        subCategoryRepository.deleteById(sId);
    }

    @Override
    public List<SubCategory> getAllSubcategoryOfCategory(String catId) {
        List<SubCategory> subCategoryList = this.subCategoryRepository.findByCategoryId(catId);
        return subCategoryList;
    }

}
