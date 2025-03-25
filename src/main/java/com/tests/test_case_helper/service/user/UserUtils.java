package com.tests.test_case_helper.service.user;

import com.tests.test_case_helper.dto.user.UserDTO;

public interface UserUtils {
    void findUserByLogin(String login);
    UserDTO findUserByLoginAndReturn(String login);
    void findRegisteredUser(String login);
    void validateAdminToken(String token);
}
