package com.tests.test_case_helper.service.suite.run;

import com.tests.test_case_helper.dto.suite.run.RunTestSuiteResponseDTO;
import com.tests.test_case_helper.entity.TestCaseRunResult;
import com.tests.test_case_helper.entity.TestSuiteRunSession;
import com.tests.test_case_helper.entity.User;
import com.tests.test_case_helper.entity.cases.TestCase;
import com.tests.test_case_helper.repository.TestSuiteRunSessionRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TestSuiteRunSessionUtil {
    List<TestCaseRunResult> createRunResults(List<TestCase> testCases, TestSuiteRunSession runSession);
    RunTestSuiteResponseDTO mapSessionToDto(TestSuiteRunSession session);
    RunTestSuiteResponseDTO getRunTestSuiteSessionById(Long id, Long sessionId, Pageable pageable);
    void validateTestSuiteRunSession(User user, TestSuiteRunSessionRepository.TestSuiteRunSessionSlimProjection runSession);
}
