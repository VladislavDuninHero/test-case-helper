package com.tests.test_case_helper.repository.projections;

import com.tests.test_case_helper.entity.User;

import java.time.LocalDateTime;

public interface TestSuiteRunSessionProjection {
    Long getId();
    String getTestSuiteTitle();
    User getExecutedBy();
    String getEnvironment();
    String getStatus();
    LocalDateTime getStartTime();
    LocalDateTime getEndTime();
}
