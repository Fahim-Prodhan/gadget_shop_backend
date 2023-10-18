package com.fahim.Ecommerce.service;

import com.fahim.Ecommerce.model.shop.SubCategory;

import java.util.List;

public interface SubCategoryService {
   SubCategory addSubCategory (SubCategory subCategory);
   SubCategory updateSubCategory(SubCategory subCategory);
   SubCategory getSingleSubCategory(String sId);
   List<SubCategory> getAllSubCategory();
   void deleteSubCategory(String sId);

   List<SubCategory> getAllSubcategoryOfCategory(String catId);

}
