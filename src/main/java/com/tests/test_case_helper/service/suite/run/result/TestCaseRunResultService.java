package com.tests.test_case_helper.service.suite.run.result;

import com.tests.test_case_helper.constants.ExceptionMessage;
import com.tests.test_case_helper.dto.suite.run.TestSuiteRunSessionStatisticDTO;
import com.tests.test_case_helper.entity.TestCaseRunResult;
import com.tests.test_case_helper.exceptions.TestCaseResultNotFoundException;
import com.tests.test_case_helper.repository.TestCaseRunResultsRepository;
import com.tests.test_case_helper.service.utils.TestSuiteRunResultMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestCaseRunResultService implements TestCaseResultUtils {

    private final TestCaseRunResultsRepository testCaseRunResultsRepository;
    private final TestSuiteRunResultMapper testSuiteRunResultMapper;

    public TestCaseRunResultService(
            TestCaseRunResultsRepository testCaseRunResultsRepository,
            TestSuiteRunResultMapper testSuiteRunResultMapper
    ) {
        this.testCaseRunResultsRepository = testCaseRunResultsRepository;
        this.testSuiteRunResultMapper = testSuiteRunResultMapper;
    }

    @Override
    public TestCaseRunResult getTestCaseResultInTestSuiteById(Long id) {
        return testCaseRunResultsRepository.findFirstTestCaseResultById(id)
                .orElseThrow(() -> new TestCaseResultNotFoundException(
                        ExceptionMessage.TEST_CASE_RESULT_NOT_FOUND_EXCEPTION_MESSAGE)
                );
    }

    @Override
    public List<TestSuiteRunSessionStatisticDTO> getStatusStatisticsBySessionId(Long id) {
        return testCaseRunResultsRepository.countAllStatusesForSession(id)
                .stream()
                .map(testSuiteRunResultMapper::mapStatisticToDto)
                .collect(Collectors.toList());
    }
}
