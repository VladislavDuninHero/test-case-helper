package com.tests.test_case_helper.service.validation.manager.impl;

import com.tests.test_case_helper.config.validation.ValidationConfig;
import com.tests.test_case_helper.repository.TestSuiteRunSessionRepository;
import com.tests.test_case_helper.service.validation.manager.ValidationManager;
import org.springframework.stereotype.Service;

@Service
public class TestSuiteRunSessionValidationManager
        implements ValidationManager<TestSuiteRunSessionRepository.TestSuiteRunSessionSlimProjection> {

    private final ValidationConfig validationConfig;

    public TestSuiteRunSessionValidationManager(ValidationConfig validationConfig) {
        this.validationConfig = validationConfig;
    }

    @Override
    public void validate(TestSuiteRunSessionRepository.TestSuiteRunSessionSlimProjection value) {
        validationConfig.configureTestSuiteRunSessionValidators()
                .forEach(validator -> validator.validate(value));
    }
}
