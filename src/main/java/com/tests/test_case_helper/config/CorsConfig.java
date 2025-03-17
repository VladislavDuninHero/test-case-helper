package com.tests.test_case_helper.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Применить CORS ко всем эндпоинтам
                .allowedOrigins("http://localhost:5173") // Разрешенный источник (ваш фронтенд)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Разрешенные HTTP-методы
                .allowedHeaders("*") // Разрешенные заголовки
                .allowCredentials(true)
                .maxAge(3600); // Время кэширования preflight-запросов (в секундах)
    }
}
