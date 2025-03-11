package com.tests.test_case_helper.service.user.impl;

import com.tests.test_case_helper.constants.ExceptionMessage;
import com.tests.test_case_helper.dto.user.registration.UserRegistrationResponseDTO;
import com.tests.test_case_helper.entity.User;
import com.tests.test_case_helper.exceptions.UserIsAlreadyRegisteredException;
import com.tests.test_case_helper.exceptions.UserNotFoundException;
import com.tests.test_case_helper.repository.UserRepository;
import com.tests.test_case_helper.service.user.UserUtils;
import com.tests.test_case_helper.service.utils.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserUtilsImpl implements UserUtils {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserUtilsImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public void findUserByLogin(String login) {
        userRepository.findUserByLogin(login)
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

}
