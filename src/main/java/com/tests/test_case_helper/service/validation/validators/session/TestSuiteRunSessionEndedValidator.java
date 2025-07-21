package com.tests.test_case_helper.service.validation.validators.session;

import com.tests.test_case_helper.constants.ExceptionMessage;
import com.tests.test_case_helper.enums.TestSuiteRunStatus;
import com.tests.test_case_helper.exceptions.TestSuiteRunSessionAlreadyEndedException;
import com.tests.test_case_helper.repository.TestSuiteRunSessionRepository;
import com.tests.test_case_helper.service.validation.validators.BaseValidator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class TestSuiteRunSessionEndedValidator extends BaseValidator<TestSuiteRunSessionRepository.TestSuiteRunSessionSlimProjection, TestSuiteRunSessionRepository.TestSuiteRunSessionSlimProjection> {
    @Override
    public TestSuiteRunSessionRepository.TestSuiteRunSessionSlimProjection validate(TestSuiteRunSessionRepository.TestSuiteRunSessionSlimProjection value) {

        if (value.getStatus().equals(TestSuiteRunStatus.ENDED.name())) {
            throw new TestSuiteRunSessionAlreadyEndedException(
                    ExceptionMessage.TEST_SUITE_RUN_SESSION_ALREADY_ENDED_EXCEPTION_MESSAGE
            );
        }

        if (getNext() != null) {
            return getNext().validate(value);
        }

        return value;
    }
}
