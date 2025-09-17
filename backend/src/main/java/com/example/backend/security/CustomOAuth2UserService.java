package com.example.backend.security;

import com.example.backend.entity.Users;
import com.example.backend.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UsersRepository usersRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println("Google attributes: " + oAuth2User.getAttributes());

        String email = oAuth2User.getAttribute("email");

        // Lấy name từ email (phần trước @)
        String nameFromEmail = null;
        if (email != null && email.contains("@")) {
            nameFromEmail = email.substring(0, email.indexOf("@"));
        }

        Optional<Users> existingUserOpt = usersRepository.findByEmail(email);

        if (existingUserOpt.isEmpty() && email != null) {
            Users user = new Users();
            user.setEmail(email);
            user.setName(nameFromEmail != null ? nameFromEmail : "GoogleUser"); // Gán name = trước @
            user.setRole("CUSTOMER");
            user.setStatus(true);
            usersRepository.save(user);

            System.out.println("User saved: " + email);
        }

        return oAuth2User;
    }

}
