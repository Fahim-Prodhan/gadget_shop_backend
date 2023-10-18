package com.fahim.Ecommerce.model.cart;

import com.fahim.Ecommerce.model.AppUser;
import com.fahim.Ecommerce.model.shop.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cartId;

    @ManyToOne
    private Product product;

    @ManyToOne
    private AppUser user;

    public Cart(Product product, AppUser user) {
        this.product = product;
        this.user = user;
    }
}
