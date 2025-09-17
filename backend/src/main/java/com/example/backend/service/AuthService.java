package com.example.backend.service;

import com.example.backend.dto.RegisterRequest;

public interface AuthService {
    String register(RegisterRequest request);
    String verifyToken(String token);
    String forgotPassword(String email);
    String resetPassword(String token, String newPassword);
}
