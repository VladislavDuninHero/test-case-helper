package com.tests.test_case_helper.service.validation.configurator;

import com.tests.test_case_helper.service.validation.validators.BaseValidator;

import java.util.List;

public interface ValidationConfigurator {
    <T, R> List<BaseValidator<T, R>> config(List<BaseValidator<T, R>> validators);
}
