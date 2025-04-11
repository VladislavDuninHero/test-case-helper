package com.tests.test_case_helper.dto.exception;

public class TestCaseExceptionDTO extends AbstractException {
    public TestCaseExceptionDTO(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
