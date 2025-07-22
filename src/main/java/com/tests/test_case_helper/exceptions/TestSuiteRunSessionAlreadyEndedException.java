package com.tests.test_case_helper.exceptions;

public class TestSuiteRunSessionAlreadyEndedException extends RuntimeException {
    public TestSuiteRunSessionAlreadyEndedException(String message) {
        super(message);
    }
}
