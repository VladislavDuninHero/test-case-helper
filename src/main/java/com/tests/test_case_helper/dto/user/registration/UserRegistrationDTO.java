package com.tests.test_case_helper.dto.user.registration;

import com.tests.test_case_helper.constants.ExceptionMessage;
import com.tests.test_case_helper.enums.Roles;
import com.tests.test_case_helper.service.validation.annotations.EnumValidate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 20)
    private String login;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 20)
    private String password;

    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    @EnumValidate(enumClass = Roles.class, message = ExceptionMessage.ROLE_NOT_FOUND_EXCEPTION_MESSAGE)
    private String role;

    @NotNull
    @NotEmpty
    private String token;
}
