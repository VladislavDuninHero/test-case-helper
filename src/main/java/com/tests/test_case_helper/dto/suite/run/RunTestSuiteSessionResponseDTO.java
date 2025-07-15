package com.tests.test_case_helper.dto.suite.run;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RunTestSuiteSessionResponseDTO {
    private Long runSessionId;
    private String message;
}
