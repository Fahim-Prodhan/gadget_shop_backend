package com.fahim.Ecommerce.model.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionDetails {
    private String orderId;
    private String currency;
    private Integer amount;
    private String key;
}
