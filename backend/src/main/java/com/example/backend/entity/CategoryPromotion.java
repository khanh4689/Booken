package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "category_promotion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPromotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long promotionId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String name;
    private String discountType; // PERCENT, FIXED
    private BigDecimal discountValue;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean status = true;
}
