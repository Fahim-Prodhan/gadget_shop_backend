package com.fahim.Ecommerce.controller;

import com.fahim.Ecommerce.model.shop.SubCategory;
import com.fahim.Ecommerce.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/subcategories")
@RequiredArgsConstructor
public class SubCategoryController {

    private final SubCategoryService subCategoryService;


    //adding
    @PostMapping("/add")
    public ResponseEntity<SubCategory> addSubCategory (@RequestBody SubCategory subCategory) {
        return ResponseEntity.ok(this.subCategoryService.addSubCategory(subCategory));
    }

    //get single
    @GetMapping("/{sId}")
    public ResponseEntity<SubCategory> getSingleSubCategory(@PathVariable String sId) {
        return ResponseEntity.ok(this.subCategoryService.getSingleSubCategory(sId));
    }

    //get all
    @GetMapping("/all")
    public ResponseEntity<List<SubCategory>> allSubCategories() {
        return ResponseEntity.ok(this.subCategoryService.getAllSubCategory());
    }

    //update
    @PutMapping("/update")
    public ResponseEntity<SubCategory> updateSubCategory (@RequestBody SubCategory subCategory) {
        return ResponseEntity.ok(this.subCategoryService.updateSubCategory(subCategory));
    }

    //delete
    @DeleteMapping("/{sId}")
    public void deleteSubCategory(@PathVariable String sId) {
        this.subCategoryService.deleteSubCategory(sId);
    }

    //get All Subcategories of a category
    @GetMapping("/category/{catId}")
    public ResponseEntity<List<SubCategory>> getSubcategoryOfCategory(@PathVariable String catId){
        List<SubCategory> allSubcategoryOfCategory = this.subCategoryService.getAllSubcategoryOfCategory(catId);
        return ResponseEntity.ok(allSubcategoryOfCategory);
    }


}
