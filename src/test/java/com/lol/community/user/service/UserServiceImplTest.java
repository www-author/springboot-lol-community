package com.lol.community.user.service;

import com.lol.community.user.domain.User;
import com.lol.community.user.form.UserEditForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Autowired
    UserServiceImpl userService;
    @Test
    void join() throws Exception {
        User user = User.builder()
                .name("lol24")
                .email("lol124@gmail.com")
                .password("1234")
                .grade("하수")
                .build();

        Integer savedId = userService.join(user);
        User findUser = userService.findById(savedId);
        assertThat(findUser).isEqualTo(user);
    }

    @Test
    void duplicate_user() throws Exception {
        User user1 = User.builder()
                .name("lol24")
                .email("user1@gmail.com")
                .password("1234")
                .grade("하수")
                .build();

        User user2 = User.builder()
                .name("lol123")
                .email("user2@gmail.com")
                .password("1234")
                .grade("중수")
                .build();

        userService.join(user1);

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> userService.join(user2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    void update() {
        User user = User.builder()
                .name("lol24")
                .email("lol24@gmail.com")
                .password("1234")
                .grade("하수")
                .build();

        Integer savedId = userService.join(user);

        UserEditForm updateParam = new UserEditForm();
        updateParam.setName("star24");
        updateParam.setEmail(user.getEmail());
        updateParam.setPassword(user.getPassword());
        updateParam.setPwCheck(user.getPassword());

        userService.update(savedId, updateParam);
        User findUser = userService.findById(savedId);

        assertThat(findUser.getName()).isEqualTo("star24");
    }
}