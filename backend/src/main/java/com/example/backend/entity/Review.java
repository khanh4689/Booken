package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    private String name;
    private String description;
    private String images;
    private Integer stock;
    private BigDecimal price;
    private Boolean status = true;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
