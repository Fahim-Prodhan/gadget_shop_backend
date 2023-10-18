package com.fahim.Ecommerce.service.impl;

import com.fahim.Ecommerce.config.JwtAuthenticationFilter;
import com.fahim.Ecommerce.model.AppUser;
import com.fahim.Ecommerce.model.cart.Cart;
import com.fahim.Ecommerce.model.shop.Product;
import com.fahim.Ecommerce.repo.CartRepository;
import com.fahim.Ecommerce.repo.ProductRepository;
import com.fahim.Ecommerce.repo.UserRepository;
import com.fahim.Ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    private final UserRepository userRepository;

    private final CartRepository cartRepository;


    @Override
    public Product addProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public Product getSingleProduct(Long pId) {
        Product product = this.repository.findById(pId).orElse(null);
        return product;

    }

    @Override
    public List<Product> getAllProducts(int pageNumber,String searchKey) {
        Pageable p = PageRequest.of(pageNumber,4,Sort.by(Sort.Order.desc("productId")));
        Page<Product> productPage = repository.findAll(p);
        List<Product> content = productPage.getContent();

        if (searchKey.equals("")){
            List<Product> shuffledContent = new ArrayList<>(content);
            Collections.shuffle(shuffledContent);
            return shuffledContent;
        }else {
            return (List<Product>) repository.findAllByProductNameContainingIgnoreCaseOrShortDescriptionContainsIgnoreCase(searchKey, searchKey, p);
        }

    }

    @Override
    public List<Product> getNewArrivalProduct() {

        Pageable p = PageRequest.of(0, 8, Sort.by(Sort.Order.desc("productId")));
        Page<Product> newArrival = repository.findAll(p);
        List<Product> content = newArrival.getContent();
        return content;
    }

    @Override
    public void deleteProduct(Long pId) {
        repository.deleteById(pId);
    }

    @Override
    public List<Product> getProductsOfSubCategory(String subCatId) {
        List<Product> productList = this.repository.findBySubCategoryId(subCatId);

        return productList;
    }

    @Override
    public List<Product> getProductsOfCategory(String catId) {
        return repository.findProductByCategoryId(catId);
    }

    @Override
    public List<Product> getProductDetails(boolean isSingleProductCheckout, Long productId) {
        if (isSingleProductCheckout && productId != 0) {
            List<Product> list = new ArrayList<>();
            Product product = this.repository.findById(productId).orElse(null);
            list.add(product);
            return list;
        }else {
            //this will apply for cart to buy multiple product together
            String username = JwtAuthenticationFilter.CURRENT_USER;
            AppUser user = userRepository.findByUsername(username);
            List<Cart> cartsByUser = cartRepository.findCartsByUser(user);

            return cartsByUser.stream().map(x->x.getProduct()).collect(Collectors.toList());

        }
    }
}
