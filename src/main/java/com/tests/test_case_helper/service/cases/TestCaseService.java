package com.tests.test_case_helper.service.cases;

import com.tests.test_case_helper.dto.cases.CreateTestCaseDTO;
import com.tests.test_case_helper.dto.cases.CreateTestCaseResponseDTO;
import com.tests.test_case_helper.dto.cases.TestCaseDTO;
import com.tests.test_case_helper.dto.cases.UpdateTestCaseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TestCaseService {
    CreateTestCaseResponseDTO create(CreateTestCaseDTO createTestCaseDTO);
    TestCaseDTO getTestCase(Long id);
    TestCaseDTO updateTestCase(Long id, UpdateTestCaseDTO updateTestCaseDTO);
    void deleteTestCase(Long id);
    List<TestCaseDTO> getTestCasesByTestSuiteId(Long testSuiteId, Pageable pageable);
    List<TestCaseDTO> getTestCasesByTestSuiteId(Long testSuiteId);
}
