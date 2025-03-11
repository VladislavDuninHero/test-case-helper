package com.tests.test_case_helper.dto.user.registration;

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
}
