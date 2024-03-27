package com.lol.community.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
    @Bean // 특정 대상 스프링 시큐리티 기능 비활성화
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring()
                .requestMatchers(toH2Console())
                // 스웨거도  security 비활성화 추가
                .requestMatchers("/static/**","/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html");
    }
}
