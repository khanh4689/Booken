package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "supplier")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;

    private String name;
    private String phone;
    private String address;
    private Boolean status = true;
    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(mappedBy = "supplier")
    private List<Product> products;
}
