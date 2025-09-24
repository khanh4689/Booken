package com.example.backend.service;

import com.example.backend.dto.SupplierRequest;
import com.example.backend.entity.Supplier;

import java.util.List;

public interface SupplierService {
    List<Supplier> getAllSuppliers();
    Supplier createSupplier(SupplierRequest request);
    Supplier updateSupplier(Long id, SupplierRequest request);
    void deleteSupplier(Long id);
}
