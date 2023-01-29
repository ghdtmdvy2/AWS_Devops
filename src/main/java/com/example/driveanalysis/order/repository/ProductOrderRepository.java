package com.example.driveanalysis.order.repository;

import com.example.driveanalysis.order.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepository extends JpaRepository<ProductOrder,Long> {

}
