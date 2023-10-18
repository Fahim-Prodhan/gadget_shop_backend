package com.fahim.Ecommerce.controller;

import com.fahim.Ecommerce.model.cart.Cart;
import com.fahim.Ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/addToCart/{productId}")
    public ResponseEntity<Cart> addToCart(@PathVariable Long productId) {
        Cart cart = cartService.addToCart(productId);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/cartDetails")
    public ResponseEntity<List<Cart>> getCartDetails(){
        List<Cart> userCartList = this.cartService.getUserCartList();
        return ResponseEntity.ok(userCartList);
    }

    @DeleteMapping("/{cartId}")
    public void deleteCart(@PathVariable Long cartId) {
        this.cartService.deleteCart(cartId);
    }

    //count carts
    @GetMapping("/count")
    public Long countCartsByUser(){
        return this.cartService.countCartByUser();
    }

}
