package com.tests.test_case_helper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableConfigurationProperties
@EnableJpaRepositories
@EnableAspectJAutoProxy(exposeProxy = true)
public class TestCaseHelperApplication {
	public static void main(String[] args) {
		SpringApplication.run(TestCaseHelperApplication.class, args);
	}
}
