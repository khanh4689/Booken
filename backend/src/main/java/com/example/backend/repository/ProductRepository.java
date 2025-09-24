package com.example.backend.repository;

import com.example.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // View all product
    @Query("SELECT p FROM Product p ORDER BY p.updatedAt DESC, p.createdAt DESC")
    List<Product> findAllProductByNewest();
    // Check validate name product
    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndProductIdNot(String name, Long productId);

}
