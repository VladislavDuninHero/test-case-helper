package com.tests.test_case_helper.service.user;

import com.tests.test_case_helper.dto.user.UserDTO;
import com.tests.test_case_helper.dto.user.UserFullInfoDTO;
import com.tests.test_case_helper.dto.user.login.UserLoginDTO;
import com.tests.test_case_helper.dto.user.login.UserLoginResponseDTO;
import com.tests.test_case_helper.dto.user.registration.UserRegistrationDTO;
import com.tests.test_case_helper.dto.user.registration.UserRegistrationResponseDTO;

public interface UserService {
    UserRegistrationResponseDTO createUser(UserRegistrationDTO userRegistrationDTO);
    UserLoginResponseDTO login(UserLoginDTO userLoginDTO);
    UserFullInfoDTO getUserById(Long id);
    void disableUser(Long userId);
    void recoveryUser(Long userId);
    UserDTO getUserByToken(String token);
    UserFullInfoDTO getFullUserInfoByToken(String token);
    UserDTO getUserBySecurityContext();
}
