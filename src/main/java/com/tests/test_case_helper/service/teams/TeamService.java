package com.tests.test_case_helper.service.teams;

import com.tests.test_case_helper.dto.teams.*;

public interface TeamService {
    TeamSlimDTO createTeam(CreateTeamDTO createTeamDTO);
    TeamDTO getTeamById(long id);
    TeamDTO updateTeamById(long id, CreateTeamDTO updateTeam);
    void deleteTeamById(long id);
    void addTeammateById(long id, AddTeammateDTO teammate);
    TeamDTO deleteTeammateInTeamById(Long id, Long teammateId);
    TeamDTO addTeammateByLogin(Long id, AddTeammateByLdapDTO teammateLdap);
}
