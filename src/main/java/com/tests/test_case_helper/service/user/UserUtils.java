package com.tests.test_case_helper.service.user;

import com.tests.test_case_helper.dto.user.registration.UserRegistrationResponseDTO;

public interface UserUtils {
    void findUserByLogin(String login);
    void findRegisteredUser(String login);
}
