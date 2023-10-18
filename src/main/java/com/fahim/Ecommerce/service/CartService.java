package com.fahim.Ecommerce.service;

import com.fahim.Ecommerce.model.AppUser;
import com.fahim.Ecommerce.model.cart.Cart;

import java.util.List;

public interface CartService {
    Cart addToCart(Long productId);
    List<Cart> getUserCartList();
    void deleteCart(Long cartId);

    Long countCartByUser();
}
