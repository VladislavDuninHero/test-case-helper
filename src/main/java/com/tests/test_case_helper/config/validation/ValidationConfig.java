package com.tests.test_case_helper.config.validation;

import com.tests.test_case_helper.dto.project.CreateProjectDTO;
import com.tests.test_case_helper.dto.project.ProjectDTO;
import com.tests.test_case_helper.dto.teams.CreateTeamDTO;
import com.tests.test_case_helper.repository.TestSuiteRunSessionRepository;
import com.tests.test_case_helper.service.validation.configurator.ValidationConfigurator;
import com.tests.test_case_helper.service.validation.validators.BaseValidator;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ValidationConfig {

    private final ValidationConfigurator validationConfigurator;

    private final List<BaseValidator<CreateProjectDTO, CreateProjectDTO>> createProjectValidators;
    private final List<BaseValidator<TestSuiteRunSessionRepository.TestSuiteRunSessionSlimProjection, TestSuiteRunSessionRepository.TestSuiteRunSessionSlimProjection>> testSuiteRunSessionValidators;
    private final List<BaseValidator<CreateTeamDTO, CreateTeamDTO>> createTeamValidators;

    public ValidationConfig(
            ValidationConfigurator validationConfigurator,
            List<BaseValidator<CreateProjectDTO, CreateProjectDTO>> createProjectValidators,
            List<BaseValidator<TestSuiteRunSessionRepository.TestSuiteRunSessionSlimProjection, TestSuiteRunSessionRepository.TestSuiteRunSessionSlimProjection>> testSuiteRunSessionValidators,
            List<BaseValidator<CreateTeamDTO, CreateTeamDTO>> createTeamValidators
    ) {
        this.validationConfigurator = validationConfigurator;

        this.createProjectValidators = createProjectValidators;
        this.testSuiteRunSessionValidators = testSuiteRunSessionValidators;
        this.createTeamValidators = createTeamValidators;
    }

    @PostConstruct
    public List<BaseValidator<CreateProjectDTO, CreateProjectDTO>> configureProjectValidators() {
        return validationConfigurator.config(createProjectValidators);
    }

    @PostConstruct
    public List<BaseValidator<TestSuiteRunSessionRepository.TestSuiteRunSessionSlimProjection, TestSuiteRunSessionRepository.TestSuiteRunSessionSlimProjection>> configureTestSuiteRunSessionValidators() {
        return validationConfigurator.config(testSuiteRunSessionValidators);
    }

    @PostConstruct
    public List<BaseValidator<CreateTeamDTO, CreateTeamDTO>> configureCreateTeamValidators() {
        return validationConfigurator.config(createTeamValidators);
    }
}
