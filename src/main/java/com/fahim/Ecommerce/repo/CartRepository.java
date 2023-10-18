package com.fahim.Ecommerce.repo;

import com.fahim.Ecommerce.model.AppUser;
import com.fahim.Ecommerce.model.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findCartsByUser(AppUser userId);
    Long countCartsByUser(AppUser user);
}
