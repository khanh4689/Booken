package com.example.backend.service.serviceImpl;

import com.example.backend.dto.ProductRequest;
import com.example.backend.entity.Product;
import com.example.backend.entity.Supplier;
import com.example.backend.entity.Category;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.ProductRepository;
import com.example.backend.repository.SupplierRepository;
import com.example.backend.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAllProductByNewest();
    }

    public Product createProduct(@Valid ProductRequest request) {
        if (productRepository.existsByNameIgnoreCase(request.getName())) {
            throw new IllegalArgumentException("Tên sản phẩm đã tồn tại");
        }
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found"));

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .images(request.getImages())
                .stock(request.getStock())
                .price(request.getPrice())
                .status(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .category(category)
                .supplier(supplier)
                .build();

        return productRepository.save(product);
    }

    public Product updateProduct(Long id, @Valid ProductRequest request) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        // Kiểm tra tên trùng (ngoại trừ sản phẩm hiện tại)
        if (productRepository.existsByNameIgnoreCaseAndProductIdNot(request.getName(), id)) {
            throw new IllegalArgumentException("Tên sản phẩm đã tồn tại");
        }

        existingProduct.setName(request.getName());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setImages(request.getImages());
        existingProduct.setStock(request.getStock());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setUpdatedAt(LocalDateTime.now());

        // cập nhật category
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        existingProduct.setCategory(category);

        // cập nhật supplier
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found"));
        existingProduct.setSupplier(supplier);

        return productRepository.save(existingProduct);
    }


    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        if (Boolean.FALSE.equals(product.getStatus())) {
            throw new IllegalStateException("Sản phẩm đã bị vô hiệu hóa trước đó.");
        }
        product.setStatus(false); // chỉ đổi trạng thái
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);
    }
}
