package com.tests.test_case_helper.controller.user;

import com.tests.test_case_helper.constants.OfficialProperties;
import com.tests.test_case_helper.constants.ResponseMessage;
import com.tests.test_case_helper.constants.Route;
import com.tests.test_case_helper.dto.message.ResponseMessageDTO;
import com.tests.test_case_helper.dto.user.UserDTO;
import com.tests.test_case_helper.dto.user.login.UserLoginDTO;
import com.tests.test_case_helper.dto.user.login.UserLoginResponseDTO;
import com.tests.test_case_helper.dto.user.registration.UserRegistrationDTO;
import com.tests.test_case_helper.dto.user.registration.UserRegistrationResponseDTO;
import com.tests.test_case_helper.service.user.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<UserLoginResponseDTO> login(
            @RequestBody
            @Validated
            UserLoginDTO userLoginDTO
    ) {

        UserLoginResponseDTO userLoginResponseDTO = userService.login(userLoginDTO);

        return ResponseEntity.ok(userLoginResponseDTO);
    }

    @GetMapping
    public ResponseEntity<UserDTO> getUserByToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION)
            String token
    ) {
        String formattedToken = token.substring(OfficialProperties.BEARER_TOKEN_PREFIX.length());
        UserDTO userDTO = userService.getUserByToken(formattedToken);

        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping(Route.API_DELETE_ROUTE)
    @PreAuthorize("hasAuthority('DELETE_USER')")
    public ResponseEntity<ResponseMessageDTO> disableUser(
            @PathVariable
            @NotNull
            Long id
    ) {
        userService.disableUser(id);

        return ResponseEntity.ok(new ResponseMessageDTO(ResponseMessage.USER_HAS_BEEN_DISABLED_MESSAGE));
    }
}
