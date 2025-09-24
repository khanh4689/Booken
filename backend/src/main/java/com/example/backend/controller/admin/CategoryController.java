package com.example.backend.controller.admin;


import com.example.backend.dto.CategoryRequest;
import com.example.backend.entity.Category;
import com.example.backend.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    // READ
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    // CREATE
    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequest request) {
        try {
            return ResponseEntity.ok(categoryService.createCategory(request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Tạo danh mục thất bại: " + e.getMessage());
        }
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequest request) {
        try {
            return ResponseEntity.ok(categoryService.updateCategory(id, request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cập nhật danh mục thất bại: " + e.getMessage());
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok("Xóa danh mục thành công (status=false)");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Xóa danh mục thất bại: " + e.getMessage());
        }
    }
}