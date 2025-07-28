package com.tests.test_case_helper.dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTeamDTO {
    private Long id;
    private String title;
    private String teamName;
    private String description;
}
