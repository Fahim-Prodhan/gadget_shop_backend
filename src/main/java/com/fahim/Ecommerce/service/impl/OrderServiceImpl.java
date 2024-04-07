package com.fahim.Ecommerce.service.impl;

import com.fahim.Ecommerce.config.JwtAuthenticationFilter;
import com.fahim.Ecommerce.model.AppUser;
import com.fahim.Ecommerce.model.cart.Cart;
import com.fahim.Ecommerce.model.order.OrderDetails;
import com.fahim.Ecommerce.model.order.OrderInput;
import com.fahim.Ecommerce.model.order.OrderProductQuantity;
import com.fahim.Ecommerce.model.order.TransactionDetails;
import com.fahim.Ecommerce.model.shop.Product;
import com.fahim.Ecommerce.repo.CartRepository;
import com.fahim.Ecommerce.repo.OrderRepository;
import com.fahim.Ecommerce.repo.ProductRepository;
import com.fahim.Ecommerce.repo.UserRepository;
import com.fahim.Ecommerce.service.OrderService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    private static final String ORDER_PLACED = "Placed";
    private static final String  KEY = "rzp_test_vc5dcDsVEdv0Uv";
    private static final String KEY_SECRET = "O2lykOGr1zpGjcJMkZiPKgfE";
    private static final String CURRENCY = "BDT";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;


    @Override
    public void placeOrder(OrderInput orderInput,boolean isCartCheckout)  {

        List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();
        String currentUser = JwtAuthenticationFilter.CURRENT_USER;

        for (OrderProductQuantity o:productQuantityList) {

            Product product = productRepository.findById(o.getProductId()).orElse(null);
            AppUser user = userRepository.findByUsername(currentUser);

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = new Date();
            String orderDate = formatter.format(date);


            OrderDetails orderDetails = new OrderDetails(
                    orderInput.getFullName(),
                    orderInput.getAddress1(),
                    orderInput.getAddress2(),
                    orderInput.getOrderContactNumber(),
                    orderInput.getOrderAltContactNumber(),
                    ORDER_PLACED,
                    product.getDiscountPrice()*o.getOrderQuantity(),
                    orderDate,
                    o.getOrderQuantity(),
                    product,
                    user,
                    orderInput.getTransactionId()

            );
            if (!isCartCheckout) {
                List<Cart> cartList = cartRepository.findCartsByUser(user);
                cartList.stream().forEach(x->cartRepository.deleteById(x.getCartId()));
            }
            Long quantity = product.getQuantity();
            if (quantity >= o.getOrderQuantity()){
                product.setQuantity(quantity-o.getOrderQuantity());
                orderRepository.save(orderDetails);
            }else {
                throw new IllegalArgumentException("Quantity is not sufficient for the order.");
            }

        }


    }

    @Override
    public List<OrderDetails> getAllOrders(String status) {
        List<OrderDetails> orderDetails = new ArrayList<>();
        if (status.equals("allOrders")){
            orderRepository.findAll().forEach(x->orderDetails.add(x));
        } else {
            orderRepository.findAllByOrderStatus(status).forEach(
                    x-> orderDetails.add(x)
            );
        }
        return orderDetails;
    }

    @Override
    public List<OrderDetails> getOrdersByUser() {
        String username = JwtAuthenticationFilter.CURRENT_USER;
        AppUser user = userRepository.findByUsername(username);
        return this.orderRepository.findOrderDetailsByUser(user);
    }

    @Override
    public void markAsDelivered(Long orderId) {
        OrderDetails orderDetails = this.orderRepository.findById(orderId).orElse(null);
        if (orderDetails != null) {
            orderDetails.setOrderStatus("Delivered");
            orderRepository.save(orderDetails);
        }
    }

    @Override
    public TransactionDetails createTransaction(Double amount) {
        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("amount",amount*100);
            jsonObject.put("currency",CURRENCY);

            RazorpayClient razorpayClient = new RazorpayClient(KEY,KEY_SECRET);
            Order order = razorpayClient.orders.create(jsonObject);

            return prepareTransactionDetails(order);


        }catch (Exception e){
            e.getMessage();
        }
        return null;
    }

    private TransactionDetails prepareTransactionDetails(Order order){
        String orderId = order.get("id");
        String currency = order.get("currency");
        Integer amount = order.get("amount");

        TransactionDetails transactionDetails = new TransactionDetails(orderId,currency,amount,KEY);
        return transactionDetails;

    }

    @Override
    public Long getOrderCountForPlaced(String status) {
        if (status.equals("Placed")){
            Long aLong = this.orderRepository.countOrderDetailsByOrderStatus(status);
            return aLong;
        }
        return null;
    }

    @Override
    public Long CountOrders() {
        String username = JwtAuthenticationFilter.CURRENT_USER;
        AppUser user = userRepository.findByUsername(username);
        return this.orderRepository.countOrderDetailsByUser(user);
    }
}
