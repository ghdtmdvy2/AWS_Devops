package com.example.driveanalysis.order.repository;

import com.example.driveanalysis.order.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOrderRepository extends JpaRepository<ProductOrder,Long> {

    List<ProductOrder> findByIsPaidTrueAndOrdererId(Long userId);
}
