package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;

    @Column(unique = true)
    private String email;
    private String password;
    private String phone;
    private String address;
    private String role; // CUSTOMER, ADMIN, GUEST
    private Boolean status = true;
    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(mappedBy = "user")
    private List<Cart> carts;

    @OneToMany(mappedBy = "user")
    private List<Orders> orders;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;
}
