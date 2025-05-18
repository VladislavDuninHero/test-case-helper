package com.tests.test_case_helper.service.suite.utils.impl;

import com.tests.test_case_helper.constants.ExceptionMessage;
import com.tests.test_case_helper.entity.TestSuite;
import com.tests.test_case_helper.exceptions.TestSuiteNotFoundException;
import com.tests.test_case_helper.repository.TestSuiteRepository;
import com.tests.test_case_helper.service.suite.utils.TestSuiteUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class TestSuiteUtil implements TestSuiteUtils {

    private final TestSuiteRepository testSuiteRepository;

    public TestSuiteUtil(TestSuiteRepository testSuiteRepository) {
        this.testSuiteRepository = testSuiteRepository;
    }

    @Override
    public TestSuite getTestSuiteById(Long id) {
        return testSuiteRepository.getTestSuiteById(id)
                .orElseThrow(
                        () -> new TestSuiteNotFoundException(ExceptionMessage.TEST_SUITE_NOT_FOUND_EXCEPTION_MESSAGE)
                );
    }
}
