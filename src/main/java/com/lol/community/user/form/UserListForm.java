package com.lol.community.user.form;

import lombok.Data;

import java.time.LocalDateTime;

@Data
    public class UserListForm {
        private String name;
        private String email;
        private String gradeCode;
        private LocalDateTime createdAt;
    }

