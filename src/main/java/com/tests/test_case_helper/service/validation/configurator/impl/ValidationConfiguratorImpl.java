package com.tests.test_case_helper.service.validation.configurator.impl;

import com.tests.test_case_helper.service.validation.configurator.ValidationConfigurator;
import com.tests.test_case_helper.service.validation.validators.BaseValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidationConfiguratorImpl implements ValidationConfigurator {

    @Override
    public <T, R> List<BaseValidator<T, R>> config(List<BaseValidator<T, R>> validators) {
        for (int i = 1; i < validators.size(); i++) {
            validators.get(i - 1).setNext(validators.get(i));
        }

        return validators;
    }

}
