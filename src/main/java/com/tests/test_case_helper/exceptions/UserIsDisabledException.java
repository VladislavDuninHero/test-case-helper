package com.tests.test_case_helper.exceptions;

public class UserIsDisabledException extends RuntimeException {
  public UserIsDisabledException(String message) {
    super(message);
  }
}
