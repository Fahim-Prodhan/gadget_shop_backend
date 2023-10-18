package com.fahim.Ecommerce.service;

import com.fahim.Ecommerce.model.order.OrderDetails;
import com.fahim.Ecommerce.model.order.OrderInput;
import com.fahim.Ecommerce.model.order.TransactionDetails;
import org.aspectj.weaver.ast.Or;

import java.util.List;

public interface OrderService {
    void placeOrder(OrderInput orderInput,boolean isCartCheckout) ;

    List<OrderDetails> getAllOrders(String status);

    List<OrderDetails> getOrdersByUser();

    void markAsDelivered(Long orderId);

    TransactionDetails createTransaction(Double amount);

    Long getOrderCountForPlaced(String status);

    Long CountOrders();
}
