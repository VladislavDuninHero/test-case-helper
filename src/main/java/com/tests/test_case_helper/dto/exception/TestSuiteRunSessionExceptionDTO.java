package com.tests.test_case_helper.dto.exception;

import com.tests.test_case_helper.dto.suite.run.RunTestSuiteSessionDTO;
import lombok.Getter;

import java.util.List;

public class TestSuiteRunSessionExceptionDTO extends AbstractException{

    @Getter
    private List<RunTestSuiteSessionDTO> activeSessions;

    public TestSuiteRunSessionExceptionDTO(
            String errorCode,
            String errorMessage,
            List<RunTestSuiteSessionDTO> activeSessions
    ) {
        super(errorCode, errorMessage);
        this.activeSessions = activeSessions;
    }
}
