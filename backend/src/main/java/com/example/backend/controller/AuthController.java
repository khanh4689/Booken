package com.example.backend.controller;

import com.example.backend.dto.ForgotPasswordRequest;
import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.RegisterRequest;
import com.example.backend.dto.ResetPasswordRequest;
import com.example.backend.entity.Users;
import com.example.backend.repository.UsersRepository;
import com.example.backend.security.JwtUtil;
import com.example.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

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
    // ========== LOGIN SUCCESS FOR OAUTH2 ==========
    @GetMapping("/login/success")
    public String loginSuccess(OAuth2AuthenticationToken authentication) {
        OAuth2User user = authentication.getPrincipal();
        return "Hello " + user.getAttribute("name") + ", email: " + user.getAttribute("email");
    }

    // ========== REGISTER ==========
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        String response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    // ========== VERIFY EMAIL ==========
    @GetMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam("token") String token) {
        String response = authService.verifyToken(token);
        return ResponseEntity.ok(response);
    }

    // ========== FORGOT PASSWORD ==========
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        String response = authService.forgotPassword(request.getEmail());
        return ResponseEntity.ok(response);
    }

    // ========== RESET PASSWORD ==========
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        String response = authService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok(response);
    }
    // ========== LOGOUT SUCCESS ==========
    @GetMapping("/logout/success")
    public ResponseEntity<String> logoutSuccess() {
        return ResponseEntity.ok("Bạn đã đăng xuất thành công!");
    }
}
