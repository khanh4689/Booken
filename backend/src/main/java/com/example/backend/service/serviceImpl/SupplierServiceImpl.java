package com.example.backend.service.serviceImpl;

import com.example.backend.dto.SupplierRequest;
import com.example.backend.entity.Supplier;
import com.example.backend.repository.SupplierRepository;
import com.example.backend.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;

    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAllByStatusTrueOrderByStartDateDesc();
    }

    @Override
    public Supplier createSupplier(SupplierRequest request) {
        Supplier supplier = Supplier.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .status(true)
                .startDate(LocalDate.now())
                .build();
        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier updateSupplier(Long id, SupplierRequest request) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy supplier"));

        supplier.setName(request.getName());
        supplier.setPhone(request.getPhone());
        supplier.setAddress(request.getAddress());
        supplier.setEndDate(LocalDate.now()); // optional: ngày update

        return supplierRepository.save(supplier);
    }

    @Override
    public void deleteSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy supplier"));
        supplier.setStatus(false); // chỉ set status false
        supplierRepository.save(supplier);
    }
}