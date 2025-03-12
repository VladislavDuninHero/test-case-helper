package com.tests.test_case_helper.dto.cases;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTestCaseResponseDTO {
    private String title;
    private String preCondition;
    private String steps;
    private String expectedResult;
}
