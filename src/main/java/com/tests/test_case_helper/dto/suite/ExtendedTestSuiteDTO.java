package com.tests.test_case_helper.dto.suite;

import com.tests.test_case_helper.dto.cases.TestCaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExtendedTestSuiteDTO {
    private String title;
    private String description;
    private List<TestCaseDTO> testCases;
    private String project;
}
