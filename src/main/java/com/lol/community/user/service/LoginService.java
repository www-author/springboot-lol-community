package com.lol.community.user.service;

import com.lol.community.user.domain.User;
import com.lol.community.user.repository.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepositoryImpl userRepository;
    private final PasswordEncoder passwordEncoder;

    public User login(String loginName, String password) {
        return userRepository.findByName(loginName)
                .filter(u -> passwordEncoder.matches(password, u.getPassword()))
                .orElse(null);
    }

}
