package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "cart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    private Boolean status = true;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems;
}
