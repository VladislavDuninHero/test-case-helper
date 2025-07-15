package com.tests.test_case_helper.dto.suite.run;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RunTestSuiteSessionDTO {
    private Long runSessionId;
    private Long testSuiteId;
    private String testSuiteTitle;
    private String environment;
}
