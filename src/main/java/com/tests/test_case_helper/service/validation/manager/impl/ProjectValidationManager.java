package com.tests.test_case_helper.service.validation.manager.impl;

import com.tests.test_case_helper.config.validation.ValidationConfig;
import com.tests.test_case_helper.dto.project.CreateProjectDTO;
import com.tests.test_case_helper.service.validation.manager.ValidationManager;
import org.springframework.stereotype.Service;

@Service
public class ProjectValidationManager implements ValidationManager<CreateProjectDTO> {

    private final ValidationConfig validationConfig;

    public ProjectValidationManager(ValidationConfig validationConfig) {
        this.validationConfig = validationConfig;
    }

    @Override
    public void validate(CreateProjectDTO value) {
        validationConfig.configureProjectValidators().forEach(validator -> validator.validate(value));
    }
}
