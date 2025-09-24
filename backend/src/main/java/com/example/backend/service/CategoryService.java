package com.example.backend.service;
import com.example.backend.entity.Category;
import com.example.backend.dto.CategoryRequest;


import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category createCategory(CategoryRequest request);
    Category updateCategory(Long id, CategoryRequest request);
    void deleteCategory(Long id);
}
