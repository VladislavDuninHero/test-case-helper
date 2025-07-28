package com.tests.test_case_helper.service.utils.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
@Component("projectCacheKeyGenerator")
public class ProjectKeyGeneratorService implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return extractProjectId(params) + "::" + authentication.getName();
    }

    private Long extractProjectId(Object... params) {

        Object projectIdObject = Arrays.stream(params)
                .filter(param -> param instanceof Long || param instanceof Integer || param instanceof String)
                .findFirst()
                .orElse(null);

        if (projectIdObject instanceof Long) {
            return (Long) projectIdObject;
        } else if (projectIdObject instanceof Integer) {
            return ((Integer) projectIdObject).longValue();
        } else if (projectIdObject instanceof String) {
            return Long.parseLong((String) projectIdObject);
        }

        return null;
    }
}
