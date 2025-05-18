package com.tests.test_case_helper.service.suite;

import com.tests.test_case_helper.dto.suite.*;
import org.springframework.data.domain.Pageable;

public interface TestSuiteService {
    CreateTestSuiteResponseDTO createTestSuite(CreateTestSuiteDTO createTestSuiteDTO);
    TestSuiteDTO updateTestSuiteById(Long id, UpdateTestSuiteDTO updateTestSuiteDTO);
    ExtendedTestSuiteDTO getTestSuite(Long id);
    ExtendedTestSuiteDTO getTestSuite(Long id, Pageable pageable);
    void deleteTestSuite(Long id);
    TestSuiteDTO getSlimTestSuite(Long id);
}
