package com.tests.test_case_helper.dto.user.login;

import com.tests.test_case_helper.dto.jwt.JwtDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponseDTO {
    private String login;
    private JwtDTO tokenInfo;
}
