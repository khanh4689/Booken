package com.example.backend.service.serviceImpl;

import com.example.backend.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${frontend.url:http://localhost:8080}")
    private String frontendUrl; // URL frontend hoặc verify link base

    @Override
    public void sendVerificationEmail(String to, String token) {
        String subject = "Verify Your Email Address";
        String link = frontendUrl + "/api/auth/verify?token=" + token;

        String htmlContent = """
                <html>
                <body style="font-family: Arial, sans-serif; background-color:#f4f4f4; padding:20px;">
                    <div style="max-width:600px; margin:auto; background-color:#fff; padding:30px; border-radius:8px; box-shadow:0 0 10px rgba(0,0,0,0.1);">
                        <img src="https://i.ibb.co/3WmQz9x/booken-logo.png" alt="Booken Logo" style="width:120px; display:block; margin-bottom:20px;">
                        <h2 style="color:#333;">Welcome to Booken!</h2>
                        <p>Hi,</p>
                        <p>Thank you for registering. Please verify your email address to activate your account:</p>
                        <a href="%s" style="display:inline-block; padding:12px 25px; margin:20px 0; background-color:#007bff; color:#fff; text-decoration:none; border-radius:5px; font-weight:bold;">Verify Email</a>
                        <p>If the button doesn’t work, copy and paste this link into your browser:</p>
                        <p style="color:#555; word-break:break-all;">%s</p>
                        <hr style="margin:30px 0; border:none; border-top:1px solid #eee;">
                        <p style="font-size:12px; color:#999;">
                            If you did not create this account, you can safely ignore this email.<br>
                            &copy; 2025 Booken. All rights reserved.
                        </p>
                    </div>
                </body>
                </html>
                """.formatted(link, link);

        sendHtmlEmail(to, subject, htmlContent);
    }

    @Override
    public void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true = HTML

            mailSender.send(message);
            System.out.println("✅ Email sent to: " + to);
        } catch (Exception e) {
            System.err.println("❌ Failed to send email to " + to + ": " + e.getMessage());
        }
    }

    @Override
    public void sendPasswordResetEmail(String to, String token) {
        String subject = "Reset Your Password";
        String link = "http://localhost:8080/reset-password?token=" + token; // frontend link

        String htmlContent = """
            <html>
            <body style="font-family: Arial, sans-serif; background-color:#f4f4f4; padding:20px;">
                <div style="max-width:600px; margin:auto; background-color:#fff; padding:30px; border-radius:8px; box-shadow:0 0 10px rgba(0,0,0,0.1);">
                    <h2 style="color:#333;">Reset Your Password</h2>
                    <p>Click the button below to reset your password:</p>
                    <a href="%s" style="display:inline-block; padding:12px 25px; margin:20px 0; background-color:#007bff; color:#fff; text-decoration:none; border-radius:5px; font-weight:bold;">Reset Password</a>
                    <p>If the button doesn’t work, copy and paste this link into your browser:</p>
                    <p style="color:#555; word-break:break-all;">%s</p>
                </div>
            </body>
            </html>
            """.formatted(link, link);

        sendHtmlEmail(to, subject, htmlContent);
    }

    @Override
    public void sendBulkHtmlEmail(List<String> toList, String subject, String htmlContent) {
        for (String to : toList) {
            sendHtmlEmail(to, subject, htmlContent);
        }
    }
}
