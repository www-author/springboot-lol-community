package com.lol.community.user.form;

import lombok.Data;

@Data
public class UserEditForm {
    private String name;
    private String email;
    private String password;
    private String pwCheck;
}
