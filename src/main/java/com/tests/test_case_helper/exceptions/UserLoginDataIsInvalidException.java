package com.tests.test_case_helper.exceptions;

public class UserLoginDataIsInvalidException extends RuntimeException {
    public UserLoginDataIsInvalidException(String message) {
        super(message);
    }
}
