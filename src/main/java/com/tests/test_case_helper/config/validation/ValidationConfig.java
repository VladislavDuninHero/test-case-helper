package com.tests.test_case_helper.config.validation;

import com.tests.test_case_helper.dto.project.CreateProjectDTO;
import com.tests.test_case_helper.service.validation.configurator.ValidationConfigurator;
import com.tests.test_case_helper.service.validation.validators.BaseValidator;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ValidationConfig {

    private final ValidationConfigurator validationConfigurator;

    private final List<BaseValidator<CreateProjectDTO, CreateProjectDTO>> createProjectValidators;

    public ValidationConfig(
            ValidationConfigurator validationConfigurator,
            List<BaseValidator<CreateProjectDTO, CreateProjectDTO>> createProjectValidators
    ) {
        this.validationConfigurator = validationConfigurator;

        this.createProjectValidators = createProjectValidators;
    }

    @PostConstruct
    public List<BaseValidator<CreateProjectDTO, CreateProjectDTO>> configureProjectValidators() {
        return validationConfigurator.config(createProjectValidators);
    }

}
