package com.tests.test_case_helper.config;

import com.tests.test_case_helper.constants.Route;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UriConfig {

    @Bean
    public List<String> allowedUri() {
        return List.of(
                Route.API_FULL_REGISTRATION_ROUTE,
                Route.API_FULL_LOGIN_ROUTE
        );
    }
}
