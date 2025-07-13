package com.tests.test_case_helper.service.suite;

import com.tests.test_case_helper.dto.message.ResponseMessageDTO;
import com.tests.test_case_helper.dto.suite.*;
import com.tests.test_case_helper.dto.suite.run.RunTestSuiteResponseDTO;
import com.tests.test_case_helper.dto.suite.run.RunTestSuiteSessionDTO;
import com.tests.test_case_helper.dto.suite.run.RunTestSuiteSessionResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TestSuiteService {
    CreateTestSuiteResponseDTO createTestSuite(CreateTestSuiteDTO createTestSuiteDTO);
    TestSuiteDTO updateTestSuiteById(Long id, UpdateTestSuiteDTO updateTestSuiteDTO);
    ExtendedTestSuiteDTO getTestSuite(Long id);
    ExtendedTestSuiteDTO getTestSuite(Long id, Pageable pageable);
    void deleteTestSuite(Long id);
    RunTestSuiteSessionResponseDTO runTestSuite(Long id, String env);
    RunTestSuiteResponseDTO getRunTestSuiteSessionById(Long id, Long sessionId, Pageable pageable);
    TestSuiteDTO getSlimTestSuite(Long id);
    List<RunTestSuiteSessionDTO> getActiveRunTestSuiteSessions();
    ResponseMessageDTO deleteRunTestSuiteSessionById(Long id, Long sessionId);
}
