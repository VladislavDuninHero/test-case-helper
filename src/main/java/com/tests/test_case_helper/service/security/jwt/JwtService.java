package com.tests.test_case_helper.service.security.jwt;

import com.tests.test_case_helper.entity.Permission;
import com.tests.test_case_helper.entity.Role;
import io.jsonwebtoken.Claims;

import java.util.Set;

public interface JwtService {
    String generateAccessToken(String login);
    String generateRefreshToken(String login);
    Claims validateToken(String token);
    String refreshAccessToken(String refreshToken);
    Set<Permission> extractPermissions(Set<Role> roles);
}
