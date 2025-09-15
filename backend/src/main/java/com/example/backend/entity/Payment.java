package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    private String paymentMethod; // VNPAY, COD, CREDIT_CARD
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String status; // PENDING, SUCCESS, FAILED
    private String transactionId;
}
