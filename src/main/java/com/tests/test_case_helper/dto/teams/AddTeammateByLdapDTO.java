package com.tests.test_case_helper.dto.teams;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddTeammateByLdapDTO {

    @NotEmpty
    @NotBlank
    @NotNull
    private String teammateLdap;
}
