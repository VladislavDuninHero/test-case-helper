package com.tests.test_case_helper.service.user.impl;

import com.tests.test_case_helper.constants.ExceptionMessage;
import com.tests.test_case_helper.dto.user.UserDTO;
import com.tests.test_case_helper.entity.User;
import com.tests.test_case_helper.exceptions.InvalidAdminTokenException;
import com.tests.test_case_helper.exceptions.UserIsAlreadyRegisteredException;
import com.tests.test_case_helper.exceptions.UserLoginDataIsInvalidException;
import com.tests.test_case_helper.exceptions.UserNotFoundException;
import com.tests.test_case_helper.properties.AdminSecretProperties;
import com.tests.test_case_helper.repository.UserRepository;
import com.tests.test_case_helper.service.user.UserUtils;
import com.tests.test_case_helper.service.utils.UserMapper;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserUtilsImpl implements UserUtils {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AdminSecretProperties adminSecretProperties;
    private final PasswordEncoder passwordEncoder;

    public UserUtilsImpl(
            UserRepository userRepository,
            UserMapper userMapper,
            AdminSecretProperties adminSecretProperties,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.adminSecretProperties = adminSecretProperties;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void findUserByLogin(String login) {
        userRepository.findUserByLogin(login)
                .orElseThrow(
                        () -> new UserNotFoundException(ExceptionMessage.USER_NOT_FOUND_EXCEPTION_MESSAGE)
                );
    }

    @Override
    public UserDTO findUserByLoginAndReturn(String login) {
         User user = userRepository.findUserByLogin(login)
                .orElseThrow(
                        () -> new UserNotFoundException(ExceptionMessage.USER_NOT_FOUND_EXCEPTION_MESSAGE)
                );

        return userMapper.toUserDTO(user);
    }

    @Override
    public User findUserEntityByLoginAndReturn(String login) {
        return userRepository.findUserByLogin(login)
                .orElseThrow(
                        () -> new UserNotFoundException(ExceptionMessage.USER_NOT_FOUND_EXCEPTION_MESSAGE)
                );
    }

    @Override
    public void findRegisteredUser(String login) {

        boolean presentUser = userRepository.findUserByLogin(login).isPresent();

        if (presentUser) {
            throw new UserIsAlreadyRegisteredException(
                    ExceptionMessage.USER_ALREADY_REGISTERED_EXCEPTION_MESSAGE
            );
        }
    }

    @Override
    public User findRegisteredUserByIdAndReturn(Long userId) {
        return userRepository.findUserById(userId)
                .orElseThrow(
                        () -> new UserNotFoundException(ExceptionMessage.USER_NOT_FOUND_EXCEPTION_MESSAGE)
                );
    }

    @Override
    public void validateAdminToken(String token) {
        if (!adminSecretProperties.getToken().equals(token)) {
            throw new InvalidAdminTokenException(ExceptionMessage.FORBIDDEN_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void validateUserPassword(String loginPassword, String foundUserPassword) {
        if (!passwordEncoder.matches(loginPassword, foundUserPassword)) {
            throw new UserLoginDataIsInvalidException(ExceptionMessage.USER_LOGIN_DATA_IS_INVALID);
        }
    }

    @Override
    public User findUserBySecurityContextAndReturn() {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();

        return findUserEntityByLoginAndReturn(userLogin);
    }

}
