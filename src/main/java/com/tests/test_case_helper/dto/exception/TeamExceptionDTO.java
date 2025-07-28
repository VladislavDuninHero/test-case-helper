package com.tests.test_case_helper.dto.exception;

public class TeamExceptionDTO extends AbstractException {
    public TeamExceptionDTO(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
