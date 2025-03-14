package com.tests.test_case_helper.exceptions;

public class TestSuiteNotFoundException extends RuntimeException {
    public TestSuiteNotFoundException(String message) {
        super(message);
    }
}
