package com.tests.test_case_helper.service.teams.impl;

import com.tests.test_case_helper.dto.teams.*;
import com.tests.test_case_helper.dto.user.UserDTO;
import com.tests.test_case_helper.entity.Team;
import com.tests.test_case_helper.entity.User;
import com.tests.test_case_helper.entity.UserTeam;
import com.tests.test_case_helper.entity.embeddable.UserTeamId;
import com.tests.test_case_helper.repository.TeamRepository;
import com.tests.test_case_helper.repository.UserTeamRepository;
import com.tests.test_case_helper.service.teams.TeamService;
import com.tests.test_case_helper.service.teams.utils.TeamUtils;
import com.tests.test_case_helper.service.user.UserUtils;
import com.tests.test_case_helper.service.utils.TeamMapper;
import com.tests.test_case_helper.service.validation.manager.impl.TeamValidationManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeamManagementService implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamUtils teamUtils;
    private final TeamMapper teamMapper;
    private final UserUtils userUtils;
    private final TeamValidationManager teamValidationManager;
    private final UserTeamRepository userTeamRepository;

    public TeamManagementService(
            TeamRepository teamRepository,
            TeamMapper teamMapper,
            TeamUtils teamUtils,
            UserUtils userUtils,
            TeamValidationManager teamValidationManager,
            UserTeamRepository userTeamRepository
    ) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
        this.teamUtils = teamUtils;
        this.userUtils = userUtils;
        this.teamValidationManager = teamValidationManager;
        this.userTeamRepository = userTeamRepository;
    }

    @Override
    public TeamSlimDTO createTeam(CreateTeamDTO createTeamDTO) {
        teamValidationManager.validate(createTeamDTO);

        Team newTeam = new Team();
        newTeam.setTeamName(createTeamDTO.getTeamName());

        Team team = teamRepository.save(newTeam);

        return new TeamSlimDTO(
                team.getId(),
                team.getTeamName()
        );
    }

    @Override
    public TeamDTO getTeamById(long id) {
        Team team = teamUtils.getTeamById(id);

        Set<UserDTO> teammates = team.getTeammates().stream()
                .map(teammate -> new UserDTO(
                        teammate.getUser().getId(),
                        teammate.getUser().getLogin(),
                        teammate.getUser().getEmail()
                    )
                )
                .collect(Collectors.toSet());

        return TeamDTO.builder()
                .id(team.getId())
                .teamName(team.getTeamName())
                .teammates(teammates)
                .build();
    }

    @Override
    @Transactional
    public TeamDTO updateTeamById(long id, CreateTeamDTO updateTeamDTO) {
        teamValidationManager.validate(updateTeamDTO);

        Team team = teamUtils.getTeamById(id);
        team.setTeamName(updateTeamDTO.getTeamName());

        Team updatedTeam = teamRepository.save(team);

        return teamMapper.toDto(updatedTeam);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(
                    value = "project",
                    allEntries = true
            ),
            @CacheEvict(
                    value = "user_projects",
                    allEntries = true
            )
    })
    public void deleteTeamById(long id) {
        Team team = teamUtils.getTeamById(id);

        teamRepository.delete(team);
    }

    @Override
    @Transactional
    public void addTeammateById(long id, AddTeammateDTO teammateDTO) {
        Team team = teamUtils.getTeamById(id);
        User user = userUtils.findRegisteredUserByIdAndReturn(teammateDTO.getTeammateId());

        Set<UserTeam> teammates = team.getTeammates();

        teamUtils.findAddedTeammate(teammates, teammateDTO.getTeammateId());

        UserTeam userTeam = UserTeam.builder()
                .userTeamId(new UserTeamId(user.getId(), team.getId()))
                .team(team)
                .user(user)
                .build();

        teammates.add(userTeam);

        teamRepository.save(team);
    }

    @Override
    @Transactional
    public TeamDTO deleteTeammateInTeamById(Long id, Long teammateId) {
        Team team = teamUtils.getTeamById(id);

        Set<UserTeam> teammates = team.getTeammates();

        teamUtils.findAddedTeammateAndReturn(teammates, teammateId);

        userTeamRepository.deleteTeammateInTeamById(id, teammateId);

        List<UserTeam> updatedTeammates = userTeamRepository.getAllTeammatesInTeamById(id);

        Set<UserDTO> newTeammates = updatedTeammates.stream()
                .map(teammate -> new UserDTO(
                        teammate.getUser().getId(),
                        teammate.getUser().getLogin(),
                        teammate.getUser().getEmail())
                )
                .collect(Collectors.toSet());

        return new TeamDTO(
                id,
                team.getTeamName(),
                newTeammates
        );
    }

    @Override
    public TeamDTO addTeammateByLogin(Long id, AddTeammateByLdapDTO teammateLdap) {
        Team team = teamUtils.getTeamById(id);
        Set<UserTeam> teammates = team.getTeammates();

        teamUtils.findAddedTeammateByLogin(teammates, teammateLdap.getTeammateLdap());

        User user = userUtils.findUserEntityByLoginAndReturn(teammateLdap.getTeammateLdap());

        UserTeam userTeam = UserTeam.builder()
                .userTeamId(new UserTeamId(user.getId(), team.getId()))
                .team(team)
                .user(user)
                .build();

        teammates.add(userTeam);

        teamRepository.save(team);

        List<UserTeam> updatedTeammates = userTeamRepository.getAllTeammatesInTeamById(id);

        Set<UserDTO> newTeammates = updatedTeammates.stream()
                .map(teammate -> new UserDTO(
                        teammate.getUser().getId(),
                        teammate.getUser().getLogin(),
                        teammate.getUser().getEmail())
                )
                .collect(Collectors.toSet());

        return new TeamDTO(
                id,
                team.getTeamName(),
                newTeammates
        );
    }
}
