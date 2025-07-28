package com.tests.test_case_helper.dto.teams;

import jakarta.validation.constraints.NotBlank;
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
public class CreateTeamDTO {

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 2, max = 50)
    private String teamName;
}
