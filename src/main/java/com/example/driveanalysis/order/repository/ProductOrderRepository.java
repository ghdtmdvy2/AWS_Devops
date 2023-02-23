package com.example.driveanalysis.order.repository;

import com.example.driveanalysis.order.entity.ProductOrder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Entity;
import java.util.List;

public interface ProductOrderRepository extends JpaRepository<ProductOrder,Long> {

    @EntityGraph(attributePaths = "orderItems")
    @Query(value = "select po from ProductOrder po join fetch po.orderer where po.orderer.id = :userId and po.isPaid = True")
    List<ProductOrder> findByIsPaidTrueAndOrdererId(@Param("userId") Long userId);
}
