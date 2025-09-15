package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "coupon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    private String code;
    private String description;
    private String discountType; // PERCENT, FIXED
    private BigDecimal discountValue;
    private BigDecimal minOrderAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean status = true;
}
