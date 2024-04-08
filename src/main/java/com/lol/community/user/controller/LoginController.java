package com.lol.community.user.controller;

import com.lol.community.user.domain.User;
import com.lol.community.user.form.LoginForm;
import com.lol.community.user.login.SessionConst;
import com.lol.community.user.login.SessionValue;
import com.lol.community.user.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult,
                        HttpServletRequest request, @RequestParam(required = false) String redirectURL) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        User loginUser = loginService.login(loginForm.getName(), loginForm.getPassword());

        if (loginUser == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        SessionValue value = new SessionValue(loginUser.getId(), loginUser.getGrade());
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, value);

        log.info("userId={}, grade={}", value.getUserId(), value.getGrade());
        log.info("로그인 성공");

        if (redirectURL != null) {
            return "redirect: " + redirectURL;
        }

        return "redirect:/board/main";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }
}
