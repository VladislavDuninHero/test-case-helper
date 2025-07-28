package com.tests.test_case_helper.service.validation.validators.team;

import com.tests.test_case_helper.dto.teams.CreateTeamDTO;
import com.tests.test_case_helper.repository.TeamRepository;
import com.tests.test_case_helper.service.teams.utils.TeamUtils;
import com.tests.test_case_helper.service.validation.validators.BaseValidator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class TeamAlreadyExistsValidator extends BaseValidator<CreateTeamDTO, CreateTeamDTO> {

    private final TeamUtils teamUtils;

    public TeamAlreadyExistsValidator(TeamUtils teamUtils) {
        this.teamUtils = teamUtils;
    }

    @Override
    public CreateTeamDTO validate(CreateTeamDTO value) {
        teamUtils.findTeamByName(value.getTeamName());

        if (getNext() != null) {
            return getNext().validate(value);
        }

        return value;
    }
}
