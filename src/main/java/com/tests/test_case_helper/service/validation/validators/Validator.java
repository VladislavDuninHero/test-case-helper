package com.tests.test_case_helper.service.validation.validators;

public interface Validator<T, R> {
    T validate(R value);
}
