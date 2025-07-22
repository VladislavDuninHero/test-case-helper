package com.tests.test_case_helper.dto.suite.run;

import com.tests.test_case_helper.enums.TestCaseStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTestCaseResultResponseDTO {
    private Long testCaseResultId;
    private String actualResult;
    private TestCaseStatus status;
    private String comment;
    private List<TestSuiteRunSessionStatisticDTO> sessionStatistic;
}
