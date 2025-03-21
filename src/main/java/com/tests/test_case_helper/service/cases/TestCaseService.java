package com.tests.test_case_helper.service.cases;

import com.tests.test_case_helper.dto.cases.CreateTestCaseDTO;
import com.tests.test_case_helper.dto.cases.CreateTestCaseResponseDTO;
import com.tests.test_case_helper.dto.cases.TestCaseDTO;

import java.util.List;

public interface TestCaseService {
    CreateTestCaseResponseDTO create(CreateTestCaseDTO createTestCaseDTO);
    List<TestCaseDTO> getTestCasesByTestSuiteId(Long testSuiteId);
}
