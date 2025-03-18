package com.tests.test_case_helper.service.user;

import com.tests.test_case_helper.dto.user.UserDTO;
import com.tests.test_case_helper.dto.user.login.UserLoginDTO;
import com.tests.test_case_helper.dto.user.login.UserLoginResponseDTO;
import com.tests.test_case_helper.dto.user.registration.UserRegistrationDTO;
import com.tests.test_case_helper.dto.user.registration.UserRegistrationResponseDTO;

public interface UserService {
    UserRegistrationResponseDTO createUser(UserRegistrationDTO userRegistrationDTO);
    UserLoginResponseDTO login(UserLoginDTO userLoginDTO);
    UserDTO getUserByToken(String token);
}
