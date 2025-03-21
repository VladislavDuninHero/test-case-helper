package com.tests.test_case_helper.service.suite;

import com.tests.test_case_helper.dto.suite.CreateTestSuiteDTO;
import com.tests.test_case_helper.dto.suite.CreateTestSuiteResponseDTO;
import com.tests.test_case_helper.dto.suite.ExtendedTestSuiteDTO;

public interface TestSuiteService {
    CreateTestSuiteResponseDTO createTestSuite(CreateTestSuiteDTO createTestSuiteDTO);
    ExtendedTestSuiteDTO getTestSuite(Long id);
}
