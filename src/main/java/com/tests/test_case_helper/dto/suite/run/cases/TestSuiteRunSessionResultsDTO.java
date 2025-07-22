package com.tests.test_case_helper.dto.suite.run.cases;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestSuiteRunSessionResultsDTO {
    private Integer totalElements;
    private List<TestCaseRunResultSlimDTO> results;
}
