package com.tests.test_case_helper.dto.project;

import com.tests.test_case_helper.dto.suite.ExtendedTestSuiteDTO;
import com.tests.test_case_helper.dto.suite.TestSuiteDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExtendedProjectDTO {
    private String title;
    private String description;
    private List<TestSuiteDTO> testSuites;

}
