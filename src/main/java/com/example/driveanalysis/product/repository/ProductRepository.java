package com.example.driveanalysis.product.repository;

import com.example.driveanalysis.product.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByAuthorId(Long id);

    @Query(value = "select p from Product p join fetch p.author where p.id = :productId")
    Optional<Product> findById(@Param("productId") Long productId);
}
