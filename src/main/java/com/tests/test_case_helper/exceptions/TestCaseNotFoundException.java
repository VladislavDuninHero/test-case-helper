package com.tests.test_case_helper.exceptions;

public class TestCaseNotFoundException extends RuntimeException {
    public TestCaseNotFoundException(String message) {
        super(message);
    }
}
