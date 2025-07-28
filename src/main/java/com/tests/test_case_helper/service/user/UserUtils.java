package com.tests.test_case_helper.service.user;

import com.tests.test_case_helper.dto.user.UserDTO;
import com.tests.test_case_helper.dto.user.UserFullInfoDTO;
import com.tests.test_case_helper.entity.User;

public interface UserUtils {
    void findUserByLogin(String login);
    UserDTO findUserByLoginAndReturn(String login);
    UserFullInfoDTO findFullUserByLoginAndReturn(String login);
    User findUserEntityByLoginAndReturn(String login);
    void findRegisteredUser(String login);
    void findRegisteredUserById(Long id);
    User findRegisteredUserByIdAndReturn(Long userId);
    void validateAdminToken(String token);
    void validateUserPassword(String password, String foundUserPassword);
    User findUserBySecurityContextAndReturn();
}
