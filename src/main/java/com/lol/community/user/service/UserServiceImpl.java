package com.lol.community.user.service;

import com.lol.community.user.domain.User;
import com.lol.community.user.form.UserEditForm;
import com.lol.community.user.repository.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepositoryImpl userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Integer join(User user) throws IllegalStateException {
        validateDuplicateUser(user); // 중복 회원 검증

        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.toEncodedPassword(encodedPassword);

        userRepository.save(user);
        return user.getId();
    }

    public User findById(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    private void validateDuplicateUser(User user) throws IllegalStateException {
        Optional<User> findUser = userRepository.findByName(user.getName());
        if (findUser.isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    @Transactional
    public void update(Integer id, UserEditForm updateParam) {
        String encodedPassword = passwordEncoder.encode(updateParam.getPassword());
        updateParam.setPassword(encodedPassword);
        userRepository.update(id, updateParam);
    }

    //

    public List<User> getAllUsers() {

        List<User> userList = userRepository.findAll();
        return userList;
    }
    @Transactional
    public void updateUser(Integer id, User user) {
        // id를 사용하여 기존 사용자 정보를 가져옴
        User existingUser = userRepository.findById(id);


        if (existingUser != null) {
            // 새로운 사용자 정보로 업데이트
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setGrade(user.getGrade());

            // 사용자 정보 저장
            userRepository.save(existingUser);
        }
    }
}
