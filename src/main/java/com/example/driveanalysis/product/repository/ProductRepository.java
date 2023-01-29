package com.example.driveanalysis.product.repository;

import com.example.driveanalysis.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByAuthorId(Long id);
}
