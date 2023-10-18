package com.fahim.Ecommerce.controller;

import com.fahim.Ecommerce.model.order.OrderDetails;
import com.fahim.Ecommerce.model.order.OrderInput;
import com.fahim.Ecommerce.model.order.TransactionDetails;
import com.fahim.Ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place_order/{isCartCheckout}")
    public void placeOrder(@PathVariable boolean isCartCheckout,
                           @RequestBody OrderInput orderInput) {
        this.orderService.placeOrder(orderInput,isCartCheckout);
    }

    @GetMapping("/all/{status}")
    public ResponseEntity<List<OrderDetails>> getAllOrders(@PathVariable String status) {
        List<OrderDetails> allOrders = this.orderService.getAllOrders(status);
        return ResponseEntity.ok(allOrders);
    }

    //getting orders by the user
    @GetMapping("/userOrders")
    public ResponseEntity<List<OrderDetails>> getUserOrders(){
        List<OrderDetails> ordersByUser = this.orderService.getOrdersByUser();
        return ResponseEntity.ok(ordersByUser);
    }

    //mark as delivered
    @GetMapping("/delivered/{orderId}")
    public void markAsDelivered(@PathVariable Long orderId) {
        this.orderService.markAsDelivered(orderId);
    }

    //count order
    @GetMapping("/count")
    public Long countOrdersByUser(){
        return this.orderService.CountOrders();
    }

    //getOrderCountForPlaced
    @GetMapping("placed/count")
    public Long getOrderCountForPlaced(){
        return this.orderService.getOrderCountForPlaced("Placed");
    }

    @GetMapping("pay/{amount}")
    public TransactionDetails createTransaction(@PathVariable Double amount) {
        return orderService.createTransaction(amount);
    }
}
