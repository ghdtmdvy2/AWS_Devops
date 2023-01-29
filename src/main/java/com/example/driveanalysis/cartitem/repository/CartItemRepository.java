package com.example.driveanalysis.cartitem.repository;

import com.example.driveanalysis.cartitem.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    CartItem findByProductIdAndBuyerId(Long productId, Long buyerId);

    CartItem findByBuyerId(Long buyerId);

    List<CartItem> findAllByBuyerId(Long buyerId);
}
