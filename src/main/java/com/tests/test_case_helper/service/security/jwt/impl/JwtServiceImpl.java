package com.tests.test_case_helper.service.security.jwt.impl;

import com.tests.test_case_helper.entity.Permission;
import com.tests.test_case_helper.entity.Role;
import com.tests.test_case_helper.properties.JwtSecretProperties;
import com.tests.test_case_helper.service.role.RoleService;
import com.tests.test_case_helper.service.security.jwt.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JwtServiceImpl implements JwtService {

    private final RoleService roleService;
    private final JwtSecretProperties jwtSecretProperties;

    public JwtServiceImpl(RoleService roleService, JwtSecretProperties jwtSecretProperties) {
        this.roleService = roleService;
        this.jwtSecretProperties = jwtSecretProperties;
    }

    @Override
    public String generateAccessToken(String login) {
        Map<String, Object> claims = new HashMap<>();

        Set<Permission> permissions = extractPermissions(roleService.getRoleForUser(login));
        List<String> authorities = permissions.stream().map(Permission::getAuthority).toList();

        claims.put("authorities", authorities);

        return Jwts.builder()
                .addClaims(claims)
                .setSubject(login)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                        new Date(
                                System.currentTimeMillis() + jwtSecretProperties.getAccessExpirationDate()
                        )
                )
                .signWith(jwtSecretProperties.getSecretKey())
                .compact();
    }

    @Override
    public String generateRefreshToken(String login) {
        return Jwts.builder()
                .setSubject(login)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtSecretProperties.getRefreshExpirationDate()))
                .signWith(jwtSecretProperties.getSecretKey())
                .compact();
    }

    @Override
    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecretProperties.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        Claims claims = validateToken(refreshToken);
        String login = claims.getSubject();

        return generateAccessToken(login);
    }

    @Override
    public Set<Permission> extractPermissions(Set<Role> roles) {
        Set<Permission> permissions = new HashSet<>();

        for (Role role : roles) {
            permissions.addAll(role.getPermissions());
        }

        return permissions;
    }
}
