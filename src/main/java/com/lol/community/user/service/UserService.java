package com.lol.community.user.service;

import com.lol.community.user.domain.User;
import com.lol.community.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
