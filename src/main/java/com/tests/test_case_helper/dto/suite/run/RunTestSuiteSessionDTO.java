package com.tests.test_case_helper.dto.suite.run;

import lombok.*;

import java.time.LocalDateTime;

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
    private LocalDateTime startTime;
}
