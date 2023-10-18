package com.fahim.Ecommerce.repo;

import com.fahim.Ecommerce.model.AppUser;
import com.fahim.Ecommerce.model.order.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderDetails,Long> {
    List<OrderDetails> findOrderDetailsByUser(AppUser user);
    List<OrderDetails> findAllByOrderStatus(String status);

    Long countOrderDetailsByOrderStatus(String status);
    Long countOrderDetailsByUser(AppUser user);
}
