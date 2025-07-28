package com.tests.test_case_helper.service.validation.manager.impl;

import com.tests.test_case_helper.config.validation.ValidationConfig;
import com.tests.test_case_helper.dto.teams.CreateTeamDTO;
import com.tests.test_case_helper.service.validation.manager.ValidationManager;
import org.springframework.stereotype.Service;

@Service
public class TeamValidationManager implements ValidationManager<CreateTeamDTO> {

    private final ValidationConfig validationConfig;

    public TeamValidationManager(ValidationConfig validationConfig) {
        this.validationConfig = validationConfig;
    }

    @Override
    public void validate(CreateTeamDTO value) {
        validationConfig.configureCreateTeamValidators()
                .forEach(validator -> validator.validate(value));
    }
}
