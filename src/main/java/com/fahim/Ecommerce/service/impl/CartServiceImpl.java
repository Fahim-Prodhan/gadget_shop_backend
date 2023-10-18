package com.fahim.Ecommerce.service.impl;

import com.fahim.Ecommerce.config.JwtAuthenticationFilter;
import com.fahim.Ecommerce.model.AppUser;
import com.fahim.Ecommerce.model.cart.Cart;
import com.fahim.Ecommerce.model.shop.Product;
import com.fahim.Ecommerce.repo.CartRepository;
import com.fahim.Ecommerce.repo.ProductRepository;
import com.fahim.Ecommerce.repo.UserRepository;
import com.fahim.Ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Cart addToCart(Long productId) {
        Product product = productRepository.findById(productId).get();
        String currentUser = JwtAuthenticationFilter.CURRENT_USER;

        AppUser appUser = null;

        if (currentUser != null){
            appUser = userRepository.findByUsername(currentUser);
        }

        //to remove the duplicate cart item
        List<Cart> cartList = cartRepository.findCartsByUser(appUser);
        List<Cart> filterCartList = cartList.stream().filter(x -> x.getProduct().getProductId() == productId).collect(Collectors.toList());
        if (filterCartList.size()>0) {
            return null;
        }

        if (product != null && appUser != null) {
            Cart cart = new Cart(product,appUser);
            return cartRepository.save(cart);
        }
        return null;
    }

    @Override
    public List<Cart> getUserCartList() {
        String currentUser = JwtAuthenticationFilter.CURRENT_USER;
        AppUser user = userRepository.findByUsername(currentUser);

        List<Cart> cartsByUser = cartRepository.findCartsByUser(user);
        return cartsByUser;
    }

    @Override
    public void deleteCart(Long cartId) {
        this.cartRepository.deleteById(cartId);
    }

    @Override
    public Long countCartByUser() {
        String username = JwtAuthenticationFilter.CURRENT_USER;
        AppUser cartUser = userRepository.findByUsername(username);
        return cartRepository.countCartsByUser(cartUser);
    }
}
