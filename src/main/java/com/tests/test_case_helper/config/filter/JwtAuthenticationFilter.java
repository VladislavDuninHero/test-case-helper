package com.tests.test_case_helper.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tests.test_case_helper.config.UriConfig;
import com.tests.test_case_helper.constants.ExceptionMessage;
import com.tests.test_case_helper.constants.OfficialProperties;
import com.tests.test_case_helper.dto.exception.UserExceptionDTO;
import com.tests.test_case_helper.enums.ErrorCode;
import com.tests.test_case_helper.service.security.jwt.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UriConfig uriConfig;

    public JwtAuthenticationFilter(
            final ObjectMapper objectMapper,
            final UserDetailsService userDetailsService,
            final JwtService jwtService,
            final UriConfig uriConfig
    ) {
        this.objectMapper = objectMapper;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.uriConfig = uriConfig;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String requestUrl = request.getRequestURI();

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (
                authorizationHeader != null
                && authorizationHeader.startsWith(OfficialProperties.BEARER_TOKEN_PREFIX)
        ) {
            String token = authorizationHeader.substring(OfficialProperties.BEARER_TOKEN_PREFIX.length());

            try {

                Claims validated = jwtService.validateToken(token);
                String login = validated.getSubject();

                if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(login);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            login,
                            null,
                            userDetails.getAuthorities()
                    );

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            } catch (ExpiredJwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                UserExceptionDTO userExceptionDTO = new UserExceptionDTO(ErrorCode.EXPIRED_TOKEN.name(), e.getMessage());
                response.getWriter().write(objectMapper.writeValueAsString(userExceptionDTO));

                return;
            } catch (UsernameNotFoundException | SignatureException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                UserExceptionDTO userExceptionDTO = new UserExceptionDTO(ErrorCode.INVALID_TOKEN.name(), e.getMessage());
                response.getWriter().write(objectMapper.writeValueAsString(userExceptionDTO));

                return;
            }
        }

        if (authorizationHeader == null && !uriConfig.allowedUri().contains(requestUrl)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            UserExceptionDTO userExceptionDTO = new UserExceptionDTO(
                    ErrorCode.INVALID_TOKEN.name(),
                    ExceptionMessage.FORBIDDEN_EXCEPTION_MESSAGE
            );
            response.getWriter().write(objectMapper.writeValueAsString(userExceptionDTO));

            return;
        }

        filterChain.doFilter(request, response);
    }
}
