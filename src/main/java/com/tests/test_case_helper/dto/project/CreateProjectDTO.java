package com.tests.test_case_helper.dto.project;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectDTO {

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 100)
    private String title;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 200)
    private String description;

    @NotNull
    @Positive
    private Long teamId;
}
