package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String name;
    private Boolean status = true;
    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    @OneToMany(mappedBy = "category")
    private List<CategoryPromotion> promotions;
}
