package com.tests.test_case_helper.dto.suite.run.cases;

import com.tests.test_case_helper.dto.cases.TestCaseDTO;
import com.tests.test_case_helper.enums.TestCaseStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestCaseRunResultDTO {
    private Long id;
    private TestCaseDTO testCase;
    private String actualResult;
    private TestCaseStatus status;
    private String comment;
}
