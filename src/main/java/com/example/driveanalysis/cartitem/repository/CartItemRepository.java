package com.example.driveanalysis.cartitem.repository;

import com.example.driveanalysis.cartitem.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    CartItem findByProductIdAndBuyerId(Long productId, Long buyerId);

    CartItem findByBuyerId(Long buyerId);

    @Query(value = "select c from CartItem c join fetch c.buyer join fetch c.product where c.buyer.id = :buyerId")
    List<CartItem> findAllByBuyerId(@Param("buyerId") Long buyerId);
}
