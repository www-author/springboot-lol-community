package com.lol.community.user.controller;

import com.lol.community.user.domain.GradeCode;
import com.lol.community.user.domain.User;
import com.lol.community.user.form.UserEditForm;
import com.lol.community.user.form.UserSaveForm;
import com.lol.community.user.login.Login;
import com.lol.community.user.login.SessionValue;
import com.lol.community.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserServiceImpl userService;
    private static final List<GradeCode> gradeCodes;

    static {
        gradeCodes = new ArrayList<>();
        gradeCodes.add(new GradeCode("하수", "하수(아이언, 브론즈, 실버)"));
        gradeCodes.add(new GradeCode("중수", "중수(골드, 플래티넘, 에메랄드)"));
        gradeCodes.add(new GradeCode("고수", "고수(다이아몬드, 마스터, 챌린저)"));
    }

    @GetMapping("/add")
    public String addForm(@ModelAttribute("user") UserSaveForm userSaveForm, Model model) {
        model.addAttribute("gradeCodes", gradeCodes);
        return "users/addUserForm";
    }

    @PostMapping("/add")
    public String addForm(@Validated @ModelAttribute("user") UserSaveForm userSaveForm, BindingResult bindingResult, Model model) {
        if (!userSaveForm.getPassword().equals(userSaveForm.getPwCheck())) {
            bindingResult.reject("globalError", "비밀번호가 일치하지 않습니다.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("gradeCodes", gradeCodes);
            return "users/addUserForm";
        }

        User user = User.builder()
                .name(userSaveForm.getName())
                .email(userSaveForm.getEmail())
                .password(userSaveForm.getPassword())
                .grade(userSaveForm.getGradeCode())
                .build();

        try {
            Integer savedId = userService.join(user);
        } catch (IllegalStateException e) {
            bindingResult.reject("globalError", "이미 존재하는 회원입니다.");
            model.addAttribute("gradeCodes", gradeCodes);
            return "users/addUserForm";
        }

        return "redirect:/login";
    }

    @GetMapping("/edit")
    public String userEditForm(@ModelAttribute("userEditForm") UserEditForm userEditForm, @Login SessionValue sessionValue, Model model) {
        Integer userId = sessionValue.getUserId();
        User user = userService.findById(userId);

        userEditForm.setName(user.getName());
        userEditForm.setEmail(user.getEmail());
        userEditForm.setPassword(user.getPassword());
        userEditForm.setPwCheck(user.getPassword());

        return "users/editUserForm";
    }

    @PostMapping("/edit")
    public String userEdit(@ModelAttribute("userEditForm") UserEditForm userEditForm,
                           BindingResult bindingResult, @Login SessionValue sessionValue) {
        if (!userEditForm.getPassword().equals(userEditForm.getPwCheck())) {
            bindingResult.reject("globalError", "비밀번호가 일치하지 않습니다.");
        }

        if (bindingResult.hasErrors()) {
            return "users/editUserForm";
        }

        userService.update(sessionValue.getUserId(), userEditForm);

        return "redirect:/";
    }
}
