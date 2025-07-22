package com.tests.test_case_helper.dto.suite.run.cases;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestCaseRunResultSlimDTO {
    private Long id;
    private String testCaseTitle;
    private String status;
}
