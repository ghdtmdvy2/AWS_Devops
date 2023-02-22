package com.example.driveanalysis.order.repository;

import com.example.driveanalysis.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    void deleteAllByProductId(long productId);
}
