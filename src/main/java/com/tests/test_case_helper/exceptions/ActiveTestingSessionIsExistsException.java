package com.tests.test_case_helper.exceptions;

import com.tests.test_case_helper.dto.suite.run.RunTestSuiteSessionDTO;

import java.util.List;

public class ActiveTestingSessionIsExistsException extends RuntimeException {

    private List<RunTestSuiteSessionDTO> actveSessions;

    public ActiveTestingSessionIsExistsException(String message, List<RunTestSuiteSessionDTO> actveSessions) {
        super(message);
        this.actveSessions = actveSessions;
    }

    public List<RunTestSuiteSessionDTO> getActiveSessions() {
        return actveSessions;
    }
}
