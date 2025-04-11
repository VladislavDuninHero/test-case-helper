package com.tests.test_case_helper.service.suite;

import com.tests.test_case_helper.dto.suite.*;

public interface TestSuiteService {
    CreateTestSuiteResponseDTO createTestSuite(CreateTestSuiteDTO createTestSuiteDTO);
    TestSuiteDTO updateTestSuiteById(Long id, UpdateTestSuiteDTO updateTestSuiteDTO);
    ExtendedTestSuiteDTO getTestSuite(Long id);
    void deleteTestSuite(Long id);
    TestSuiteDTO getSlimTestSuite(Long id);
}
