package com.tests.test_case_helper.service.validation.validators.session;

import com.tests.test_case_helper.constants.ExceptionMessage;
import com.tests.test_case_helper.entity.User;
import com.tests.test_case_helper.enums.TestSuiteRunStatus;
import com.tests.test_case_helper.exceptions.TestSuiteRunSessionAlreadyEndedException;
import com.tests.test_case_helper.exceptions.TestSuiteRunSessionNotFoundException;
import com.tests.test_case_helper.repository.TestSuiteRunSessionRepository;
import com.tests.test_case_helper.service.user.UserUtils;
import com.tests.test_case_helper.service.validation.validators.BaseValidator;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class TestSuiteRunSessionUserValidator
        extends BaseValidator<TestSuiteRunSessionRepository.TestSuiteRunSessionSlimProjection, TestSuiteRunSessionRepository.TestSuiteRunSessionSlimProjection>
{
    private final UserUtils userUtils;

    public TestSuiteRunSessionUserValidator(
            UserUtils userUtils
    ) {
        this.userUtils = userUtils;
    }

    @Override
    public TestSuiteRunSessionRepository.TestSuiteRunSessionSlimProjection validate(TestSuiteRunSessionRepository.TestSuiteRunSessionSlimProjection value) {
        User user = userUtils.findUserEntityByLoginAndReturn(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );

        if (!value.getExecutedBy().equals(user)) {
            throw new TestSuiteRunSessionNotFoundException(
                    ExceptionMessage.TEST_SUITE_RUN_SESSION_NOT_FOUND_EXCEPTION_MESSAGE
            );
        }

        if (getNext() != null) {
            return getNext().validate(value);
        }

        return value;
    }
}
