package com.tests.test_case_helper.service.teams.utils.impl;

import com.tests.test_case_helper.constants.ExceptionMessage;
import com.tests.test_case_helper.entity.Team;
import com.tests.test_case_helper.entity.UserTeam;
import com.tests.test_case_helper.exceptions.TeamNameIsAlreadyTakenException;
import com.tests.test_case_helper.exceptions.TeamNotFoundException;
import com.tests.test_case_helper.exceptions.TeammateAlreadyAddedException;
import com.tests.test_case_helper.exceptions.TeammateNotFoundException;
import com.tests.test_case_helper.repository.TeamRepository;
import com.tests.test_case_helper.repository.UserTeamRepository;
import com.tests.test_case_helper.service.teams.utils.TeamUtils;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TeamManagementUtils implements TeamUtils {

    private final TeamRepository teamRepository;
    private final UserTeamRepository userTeamRepository;

    public TeamManagementUtils(
            TeamRepository teamRepository,
            UserTeamRepository userTeamRepository
    ) {
        this.teamRepository = teamRepository;
        this.userTeamRepository = userTeamRepository;
    }

    @Override
    public Team getTeamById(long id) {
        return teamRepository.findTeamById(id)
                .orElseThrow(() -> new TeamNotFoundException(ExceptionMessage.TEAM_NOT_FOUND_EXCEPTION_MESSAGE));
    }

    @Override
    public Team getTeamByName(String name) {
        return teamRepository.findTeamByName(name)
                .orElseThrow(() -> new TeamNotFoundException(ExceptionMessage.TEAM_NOT_FOUND_EXCEPTION_MESSAGE));
    }

    @Override
    public void findTeamByName(String name) {
        teamRepository.findTeamByName(name)
                .ifPresent(team -> {
                    throw new TeamNameIsAlreadyTakenException(ExceptionMessage.TEAM_NAME_IS_ALREADY_TAKEN_EXCEPTION_MESSAGE);
                });
    }

    @Override
    public UserTeam getTeammateInTeamById(long id, Long teammateId) {
        return userTeamRepository.getTeammateInTeamById(id, teammateId)
                .orElseThrow(() -> new TeammateNotFoundException(
                                ExceptionMessage.TEAMMATE_NOT_ADDED_TO_TEAM_EXCEPTION_MESSAGE
                        )
                );
    }

    @Override
    public void findAddedTeammate(Set<UserTeam> teammates, Long teammateId) {
        for (UserTeam userTeam : teammates) {
            if (userTeam.getUser().getId().equals(teammateId)) {
                throw new TeammateAlreadyAddedException(ExceptionMessage.TEAMMATE_ALREADY_ADDED_TO_TEAM_EXCEPTION_MESSAGE);
            }
        }
    }

    @Override
    public UserTeam findAddedTeammateAndReturn(Set<UserTeam> teammates, Long teammateId) {
        for (UserTeam userTeam : teammates) {
            if (userTeam.getUser().getId().equals(teammateId)) {
                return userTeam;
            }
        }

        throw new TeammateNotFoundException(ExceptionMessage.TEAMMATE_NOT_ADDED_TO_TEAM_EXCEPTION_MESSAGE);
    }

    @Override
    public void findAddedTeammateByLogin(Set<UserTeam> teammates, String teammate) {
        for (UserTeam userTeam : teammates) {
            if (userTeam.getUser().getLogin().equals(teammate)) {
                throw new TeammateAlreadyAddedException(ExceptionMessage.TEAMMATE_ALREADY_ADDED_TO_TEAM_EXCEPTION_MESSAGE);
            }
        }
    }
}
