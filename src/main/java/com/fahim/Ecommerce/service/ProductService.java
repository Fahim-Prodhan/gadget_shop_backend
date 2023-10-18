package com.fahim.Ecommerce.service;

import com.fahim.Ecommerce.model.shop.Product;

import java.util.List;

public interface ProductService {
    Product addProduct(Product product);
    Product updateProduct(Product product);
    Product getSingleProduct(Long pId);
    List<Product> getAllProducts(int pageNumber, String searchKey);

    List<Product> getNewArrivalProduct();
    void deleteProduct(Long pId);

    List<Product> getProductsOfSubCategory(String subCatId);
    List<Product> getProductsOfCategory(String catId);

    List<Product> getProductDetails(boolean isSingleProductCheckout, Long productId);
}
