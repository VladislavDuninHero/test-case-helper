package com.tests.test_case_helper.service.suite.run.result;

import com.tests.test_case_helper.dto.suite.run.TestSuiteRunSessionStatisticDTO;
import com.tests.test_case_helper.entity.TestCaseRunResult;
import com.tests.test_case_helper.repository.TestCaseRunResultsRepository;

import java.util.List;

public interface TestCaseResultUtils {
    TestCaseRunResult getTestCaseResultInTestSuiteById(Long id);
    List<TestSuiteRunSessionStatisticDTO> getStatusStatisticsBySessionId(Long id);
}
