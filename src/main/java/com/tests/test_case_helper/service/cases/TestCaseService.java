package com.tests.test_case_helper.service.cases;

import com.tests.test_case_helper.dto.cases.CreateTestCaseDTO;
import com.tests.test_case_helper.dto.cases.CreateTestCaseResponseDTO;

public interface TestCaseService {
    CreateTestCaseResponseDTO create(CreateTestCaseDTO createTestCaseDTO);
}
