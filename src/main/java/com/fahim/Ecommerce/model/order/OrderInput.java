package com.fahim.Ecommerce.model.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderInput {
    private String fullName;
    private String address1;
    private String address2;
    private String orderContactNumber;
    private String orderAltContactNumber;
    private String transactionId;
    private List<OrderProductQuantity> orderProductQuantityList;
}
