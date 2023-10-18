package com.fahim.Ecommerce.model.order;

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
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
    private String orderFullName;
    private String orderAddress1;
    private String orderAddress2;
    private String orderContactNumber;
    private String orderAltContactNumber;
    private String orderStatus;
    private Double orderAmount;

    private String orderDate;

    private Long orderQuantity;
    @ManyToOne
    private Product product;
    @ManyToOne
    private AppUser user;

    private String transactionId;

    public OrderDetails(String orderFullName, String orderAddress1, String orderAddress2, String orderContactNumber, String orderAltContactNumber, String orderStatus, Double orderAmount,String  orderDate ,Long orderQuantity, Product product, AppUser user,String transactionId) {
        this.orderFullName = orderFullName;
        this.orderAddress1 = orderAddress1;
        this.orderAddress2 = orderAddress2;
        this.orderContactNumber = orderContactNumber;
        this.orderAltContactNumber = orderAltContactNumber;
        this.orderStatus = orderStatus;
        this.orderAmount = orderAmount;
        this.orderDate = orderDate;
        this.orderQuantity = orderQuantity;
        this.product = product;
        this.user = user;
        this.transactionId = transactionId;
    }
}
