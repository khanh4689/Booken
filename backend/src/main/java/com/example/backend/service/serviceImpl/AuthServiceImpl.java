package com.example.backend.service.serviceImpl;

import com.example.backend.dto.RegisterRequest;
import com.example.backend.entity.Users;
import com.example.backend.entity.VerificationToken;
import com.example.backend.repository.UsersRepository;
import com.example.backend.repository.VerificationTokenRepository;
import com.example.backend.service.AuthService;
import com.example.backend.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public String register(RegisterRequest request) {
        if (usersRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        Users user = new Users();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setRole("CUSTOMER");
        user.setStatus(false); // chưa kích hoạt
        user.setStartDate(LocalDate.now());

        Users savedUser = usersRepository.save(user);

        // Tạo token xác thực hoặc cập nhật nếu đã tồn tại
        String token = UUID.randomUUID().toString();
        createOrUpdateVerificationToken(savedUser, token, 24); // 24h hết hạn

        // Gửi email xác thực
        emailService.sendVerificationEmail(savedUser.getEmail(), token);

        return "User registered successfully! Please check your email for verification.";
    }

    @Override
    public String verifyToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid verification token"));

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token has expired");
        }

        Users user = verificationToken.getUser();
        user.setStatus(true);
        usersRepository.save(user);

        // Xoá token sau khi xác thực
        verificationTokenRepository.delete(verificationToken);

        return "Account verified successfully!";
    }

    @Override
    public String forgotPassword(String email) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not found"));


        String token = UUID.randomUUID().toString();
        createOrUpdateVerificationToken(user, token, 1);

        // ✅ Gửi email reset
        emailService.sendPasswordResetEmail(user.getEmail(), token);

        return "Password reset email sent! Please check your inbox.";
    }

    @Override
    public String resetPassword(String token, String newPassword) {
        VerificationToken resetToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid reset token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Reset token has expired");
        }

        Users user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        usersRepository.save(user);

        // Xoá token sau khi reset
        verificationTokenRepository.delete(resetToken);

        return "Password has been reset successfully!";
    }

    /**
     * Tạo token mới hoặc cập nhật token nếu đã tồn tại
     *
     * @param user   user cần tạo token
     * @param token  token mới
     * @param hours  thời gian hết hạn (giờ)
     */
    private void createOrUpdateVerificationToken(Users user, String token, int hours) {
        Optional<VerificationToken> existingTokenOpt = verificationTokenRepository.findByUser(user);

        VerificationToken verificationToken;
        if (existingTokenOpt.isPresent()) {
            verificationToken = existingTokenOpt.get();
            verificationToken.setToken(token);
            verificationToken.setExpiryDate(LocalDateTime.now().plusHours(hours));
        } else {
            verificationToken = new VerificationToken();
            verificationToken.setUser(user);
            verificationToken.setToken(token);
            verificationToken.setExpiryDate(LocalDateTime.now().plusHours(hours));
        }

        verificationTokenRepository.save(verificationToken);
    }
}
