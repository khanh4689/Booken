package com.example.backend.service.serviceImpl;
import com.example.backend.dto.CategoryRequest;
import com.example.backend.entity.Category;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .sorted((c1, c2) -> {
                    LocalDate d1 = c1.getEndDate() != null ? c1.getEndDate() : c1.getStartDate();
                    LocalDate d2 = c2.getEndDate() != null ? c2.getEndDate() : c2.getStartDate();
                    return d2.compareTo(d1); // mới nhất trước
                })
                .toList();
    }

    @Override
    public Category createCategory(CategoryRequest request) {
        if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new IllegalArgumentException("Tên danh mục đã tồn tại");
        }

        Category category = Category.builder()
                .name(request.getName())
                .status(true)
                .startDate(LocalDate.now())
                .build();

        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy danh mục"));

        if (categoryRepository.existsByNameIgnoreCase(request.getName())
                && !category.getName().equalsIgnoreCase(request.getName())) {
            throw new IllegalArgumentException("Tên danh mục đã tồn tại");
        }

        category.setName(request.getName());
        category.setEndDate(LocalDate.now());

        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy danh mục"));

        category.setStatus(false);
        categoryRepository.save(category);
    }
}
