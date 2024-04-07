package com.lol.community.user.service;

import com.lol.community.user.domain.User;
import com.lol.community.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.lol.community.global.exception.ExceptionType.NOT_EXIST_USER;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_USER.getMessage() + "(id : " + id + ")"));
    }
}
