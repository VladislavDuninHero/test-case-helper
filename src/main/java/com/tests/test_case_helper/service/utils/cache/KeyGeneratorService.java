package com.tests.test_case_helper.service.utils.cache;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component("userCacheKeyGeneratorService")
public class KeyGeneratorService implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getName();
    }
}
