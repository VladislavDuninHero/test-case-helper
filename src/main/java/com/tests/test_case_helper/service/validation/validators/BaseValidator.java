package com.tests.test_case_helper.service.validation.validators;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseValidator<T, R> implements Validator<T, R> {
    BaseValidator<T, R> next;
}
