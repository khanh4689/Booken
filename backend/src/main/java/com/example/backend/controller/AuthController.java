package com.example.backend.controller;

import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.RegisterRequest;
import com.example.backend.entity.Users;
import com.example.backend.repository.UsersRepository;
import com.example.backend.security.JwtUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UsersRepository usersRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ========== LOGIN ==========
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Users user = usersRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return jwtUtil.generateToken(user.getEmail(), user.getRole());
    }

    // ========== REGISTER ==========
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        if (usersRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        Users user = new Users();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // hash pass
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setRole("CUSTOMER");
        user.setStatus(true);
        user.setStartDate(LocalDate.now());

        usersRepository.save(user);

        return "User registered successfully!";
    }
}
