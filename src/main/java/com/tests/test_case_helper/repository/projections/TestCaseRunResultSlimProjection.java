package com.tests.test_case_helper.repository.projections;

public interface TestCaseRunResultSlimProjection {
    Long getId();
    String getTestCaseTitle();
    String getStatus();
    String getComment();
    String getActualResult();
    Integer getCount();
}
