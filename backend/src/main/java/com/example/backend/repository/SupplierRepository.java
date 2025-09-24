package com.example.backend.repository;

import com.example.backend.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier,Long> {
    List<Supplier> findAllByStatusTrueOrderByStartDateDesc();
}
