package com.example.backend.controller.admin;

import com.example.backend.dto.SupplierRequest;
import com.example.backend.entity.Supplier;
import com.example.backend.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/suppliers")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @PostMapping
    public ResponseEntity<?> createSupplier(@Valid @RequestBody SupplierRequest request) {
        try {
            return ResponseEntity.ok(supplierService.createSupplier(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Tạo thất bại: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSupplier(@PathVariable Long id, @Valid @RequestBody SupplierRequest request) {
        try {
            return ResponseEntity.ok(supplierService.updateSupplier(id, request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cập nhật thất bại: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.ok("Đã xóa (status=false)");
    }
}