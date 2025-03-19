package com.tests.test_case_helper.dto.exception;

public class ProjectExceptionDTO extends AbstractException {
    public ProjectExceptionDTO(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
