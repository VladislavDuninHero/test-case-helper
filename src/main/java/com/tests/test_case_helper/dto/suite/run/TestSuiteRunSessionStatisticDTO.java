package com.tests.test_case_helper.dto.suite.run;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestSuiteRunSessionStatisticDTO {
    private String status;
    private Long count;
}
