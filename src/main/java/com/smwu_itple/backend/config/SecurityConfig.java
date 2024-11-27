package com.smwu_itple.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 비활성화
                .csrf(csrf -> csrf.disable())

                // HTTP Basic 인증 비활성화
                .httpBasic(httpBasic -> httpBasic.disable())

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 세션 정책 설정
                )

                // 엔드포인트 권한 설정
                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/api/users/signup", "/api/users/login").permitAll() // 인증 없이 허용
//                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}
