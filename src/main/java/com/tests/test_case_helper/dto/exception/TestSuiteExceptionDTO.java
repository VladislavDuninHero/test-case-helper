package com.tests.test_case_helper.dto.exception;

public class TestSuiteExceptionDTO extends AbstractException {
    public TestSuiteExceptionDTO(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
