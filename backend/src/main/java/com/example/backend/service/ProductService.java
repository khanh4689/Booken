package com.example.backend.service;

import com.example.backend.dto.ProductRequest;
import com.example.backend.entity.Product;
import jakarta.validation.Valid;

import java.util.List;

public interface ProductService {
    //For admin
    List<Product> getAllProducts();
    Product createProduct(@Valid ProductRequest request);
    Product updateProduct(Long id, @Valid ProductRequest request);
    void deleteProduct(Long id);
}
