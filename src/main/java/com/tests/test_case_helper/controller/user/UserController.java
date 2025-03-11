package com.tests.test_case_helper.controller.user;

import com.tests.test_case_helper.constants.Route;
import com.tests.test_case_helper.dto.user.login.UserLoginDTO;
import com.tests.test_case_helper.dto.user.login.UserLoginResponseDTO;
import com.tests.test_case_helper.dto.user.registration.UserRegistrationDTO;
import com.tests.test_case_helper.dto.user.registration.UserRegistrationResponseDTO;
import com.tests.test_case_helper.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Route.API_USER_ROUTE)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(Route.API_REGISTRATION_ROUTE)
    public ResponseEntity<UserRegistrationResponseDTO> registration(
            @RequestBody
            @Validated
            UserRegistrationDTO userRegistrationDTO
    ) {

        UserRegistrationResponseDTO createdUser = userService.createUser(userRegistrationDTO);

        return ResponseEntity.ok(createdUser);
    }

    @PostMapping(Route.API_LOGIN_ROUTE)
    public ResponseEntity<UserLoginResponseDTO> registration(
            @RequestBody
            @Validated
            UserLoginDTO userLoginDTO
    ) {

        UserLoginResponseDTO userLoginResponseDTO = userService.login(userLoginDTO);

        return ResponseEntity.ok(userLoginResponseDTO);
    }
}
