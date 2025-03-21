package com.tests.test_case_helper.dto.suite;

import com.tests.test_case_helper.dto.cases.TestCaseDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExtendedTestSuiteDTO {
    private String title;
    private String description;
    private List<TestCaseDTO> testCases;
    private Long projectId;
}
