package com.ll.exam.sbb.product.repository;

import com.ll.exam.sbb.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByAuthorId(Long id);
}
