package com.tests.test_case_helper.exceptions;

public class InvalidAdminTokenException extends RuntimeException {
    public InvalidAdminTokenException(String message) {
        super(message);
    }
}
