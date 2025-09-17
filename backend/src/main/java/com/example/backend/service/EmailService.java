package com.example.backend.service;

import java.util.List;

public interface EmailService {
    void sendVerificationEmail(String to, String token);
    void sendBulkHtmlEmail(List<String> toList, String subject, String htmlContent);
    void sendPasswordResetEmail(String to, String token);
    void sendHtmlEmail(String to, String subject, String htmlContent);

}
