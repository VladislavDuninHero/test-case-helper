package com.tests.test_case_helper.dto.suite.run;

import com.tests.test_case_helper.dto.cases.TestCaseDTO;
import com.tests.test_case_helper.enums.TestCaseStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestCaseRunResultDTO {
    private TestCaseDTO testCase;
    private String actualResult;
    private TestCaseStatus status;
    private String comment;
}
