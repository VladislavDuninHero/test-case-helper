package com.tests.test_case_helper.service.teams.utils;

import com.tests.test_case_helper.entity.Team;
import com.tests.test_case_helper.entity.UserTeam;

import java.util.Set;

public interface TeamUtils {
    Team getTeamById(long id);
    Team getTeamByName(String name);
    void findTeamByName(String name);
    UserTeam getTeammateInTeamById(long id, Long teammateId);
    void findAddedTeammate(Set<UserTeam> teammates, Long teammateId);
    UserTeam findAddedTeammateAndReturn(Set<UserTeam> teammates, Long teammateId);
    void findAddedTeammateByLogin(Set<UserTeam> teammates, String teammate);
}
