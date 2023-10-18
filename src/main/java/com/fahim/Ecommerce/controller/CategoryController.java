package com.fahim.Ecommerce.controller;

import com.fahim.Ecommerce.model.shop.Category;
import com.fahim.Ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    //adding category
    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        Category category1 = this.categoryService.addCategory(category);
        return ResponseEntity.ok(category1);
    }

    //get Single category
    @GetMapping("/{cId}")
    public ResponseEntity<Category> getSingleCategory(@PathVariable String cId) {
        return ResponseEntity.ok(this.categoryService.getCategory(cId));
    }

    //get all categories
    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(this.categoryService.getCategories());
    }

    //update
    @PutMapping("/update")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category) {
        Category category1 = this.categoryService.updateCategory(category);
        return ResponseEntity.ok(category1);
    }

    //Delete
    @DeleteMapping("/{categoryId}")
    public  void deleteCategory (@PathVariable("categoryId") String categoryId) {
        this.categoryService.deleteCategory(categoryId);
    }
}
