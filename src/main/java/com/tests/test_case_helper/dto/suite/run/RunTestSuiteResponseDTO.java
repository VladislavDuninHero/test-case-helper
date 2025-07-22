package com.tests.test_case_helper.dto.suite.run;

import com.tests.test_case_helper.dto.suite.TestSuiteDTO;
import com.tests.test_case_helper.dto.suite.run.cases.TestCaseRunResultDTO;
import com.tests.test_case_helper.dto.user.UserDTO;
import com.tests.test_case_helper.enums.TestSuiteRunStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RunTestSuiteResponseDTO {
    private Long id;
    private TestSuiteDTO testSuite;
    private UserDTO executedBy;
    private String environment;
    private List<TestCaseRunResultDTO> testCaseRunResults;
    private List<TestSuiteRunSessionStatisticDTO> sessionStatistic;
    private TestSuiteRunStatus status;
}
