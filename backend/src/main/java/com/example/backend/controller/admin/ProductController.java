package com.example.backend.controller.admin;

import com.example.backend.dto.ProductRequest;
import com.example.backend.entity.Product;
import com.example.backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // READ
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // CREATE
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequest request) {
        try {
            return ResponseEntity.ok(productService.createProduct(request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Tạo sản phẩm thất bại: " + e.getMessage());
        }
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        try {
            Product updatedProduct = productService.updateProduct(id, request);
            return ResponseEntity.ok(updatedProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cập nhật sản phẩm thất bại: " + e.getMessage());
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Xóa sản phẩm thành công (status=false)");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Xóa sản phẩm thất bại: " + e.getMessage());
        }
    }
}
