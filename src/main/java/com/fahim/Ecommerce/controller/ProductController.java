package com.fahim.Ecommerce.controller;

import com.fahim.Ecommerce.model.shop.Image;
import com.fahim.Ecommerce.model.shop.Product;
import com.fahim.Ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    //adding
    @PostMapping(value = {"/add"},consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Product> addProduct (@RequestPart("product") Product product,
                                               @RequestPart("imageFile")MultipartFile[] file) {

        try {
            Set<Image> imageSet = uploadImage(file);
            product.setProductImages(imageSet);
            Product product1 = productService.addProduct(product);
            return ResponseEntity.ok(product1);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    //get single
    @GetMapping("/{pId}")
    public ResponseEntity<Product> getSingleProduct(@PathVariable Long pId) {
        return ResponseEntity.ok(this.productService.getSingleProduct(pId));
    }

    //get all
    @GetMapping("/all")
    public ResponseEntity<List<Product>> allProducts(@RequestParam(defaultValue = "0") int pageNumber,
                                                     @RequestParam(defaultValue = "") String searchKey) {
        return ResponseEntity.ok(this.productService.getAllProducts(pageNumber,searchKey));
    }

    //update
//    @PutMapping("/update")
//    public ResponseEntity<Product> updateProduct (@RequestBody Product product) {
//        return ResponseEntity.ok(this.productService.updateProduct(product));
//    }

    //update
    @PutMapping(value = {"/update"},consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Product> updateProduct (@RequestPart("product") Product product,
                                               @RequestPart("imageFile")MultipartFile[] file) {

        try {
            Set<Image> imageSet = uploadImage(file);
            product.setProductImages(imageSet);
            Product product1 = productService.updateProduct(product);
            return ResponseEntity.ok(product1);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    //delete
    @DeleteMapping("/{pId}")
    public void deleteProduct(@PathVariable Long pId) {
        this.productService.deleteProduct(pId);
    }

    //get Products of a subcategory
    @GetMapping("/subcategory/{subId}")
    public ResponseEntity<List<Product>> getProductsOfSubcategory(@PathVariable String subId){
        List<Product> productsOfSubCategory = this.productService.getProductsOfSubCategory(subId);
        return ResponseEntity.ok(productsOfSubCategory);
    }

    //get Products of a Category
    @GetMapping("/category/{catId}")
    public ResponseEntity<List<Product>> getProductsOfCategory(@PathVariable String catId){
        List<Product> productsOfCategory = this.productService.getProductsOfCategory(catId);
        return ResponseEntity.ok(productsOfCategory);
    }

    //buying form
    @GetMapping("/product_details/{isSingleProductCheckout}/{productId}")
    public List<Product> getProductDetails(@PathVariable boolean isSingleProductCheckout,
                                  @PathVariable Long productId) {
        List<Product> productDetails = this.productService.getProductDetails(isSingleProductCheckout, productId);
        return productDetails;
    }

    //Get New product
    @GetMapping("/newArrival")
    public ResponseEntity<List<Product>> getNewArrivalProduct(){
        List<Product> newArrivalProduct = this.productService.getNewArrivalProduct();
        return ResponseEntity.ok(newArrivalProduct);
    }

    public Set<Image> uploadImage(MultipartFile[] multipartFiles) throws IOException {
        Set<Image> images = new HashSet<>();
        for (MultipartFile file: multipartFiles) {
            Image image = new Image(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );
            images.add(image);
        }
        return images;
    }



}
