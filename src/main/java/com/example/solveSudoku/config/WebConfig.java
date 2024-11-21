package com.example.solveSudoku.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 요청에 대해 CORS를 허용
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")  // CORS 허용할 도메인
                .allowedMethods("GET", "POST")  // 허용할 HTTP 메소드
                .allowedHeaders("*")  // 허용할 헤더
                .allowCredentials(true);  // 쿠키를 포함한 요청을 허용
    }
}
