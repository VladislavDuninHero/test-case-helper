package com.tests.test_case_helper.exceptions;

public class TeamNameIsAlreadyTakenException extends RuntimeException {
    public TeamNameIsAlreadyTakenException(String message) {
        super(message);
    }
}
