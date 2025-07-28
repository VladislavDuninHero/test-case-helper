package com.tests.test_case_helper.controller.teams;

import com.tests.test_case_helper.constants.ResponseMessage;
import com.tests.test_case_helper.constants.Route;
import com.tests.test_case_helper.dto.message.ResponseMessageDTO;
import com.tests.test_case_helper.dto.teams.*;
import com.tests.test_case_helper.dto.user.UserDTO;
import com.tests.test_case_helper.service.teams.TeamService;
import com.tests.test_case_helper.service.user.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Route.API_TEAMS_ROUTE)
public class TeamController {

    private final TeamService teamService;
    private final UserService userService;

    public TeamController(
            TeamService teamService,
            UserService userService
    ) {
        this.teamService = teamService;
        this.userService = userService;
    }

    @PostMapping(Route.API_CREATE_ROUTE)
    @PreAuthorize("hasAuthority('CREATE_TEAM')")
    @Validated
    public ResponseEntity<TeamSlimDTO> createTeam(
            @RequestBody
            @Valid
            CreateTeamDTO team
    ) {

        TeamSlimDTO createdTeam = teamService.createTeam(team);

        UserDTO user = userService.getUserBySecurityContext();

        teamService.addTeammateById(createdTeam.getId(), new AddTeammateDTO(user.getId()));

        return ResponseEntity.ok(createdTeam);
    }

    @GetMapping(Route.API_GET_ROUTE)
    @PreAuthorize("hasAuthority('CREATE_TEAM')")
    @Validated
    public ResponseEntity<TeamDTO> getTeamById(
            @PathVariable
            @NotNull
            @Positive
            long id
    ) {

        TeamDTO team = teamService.getTeamById(id);

        return ResponseEntity.ok(team);
    }

    @PutMapping(Route.API_UPDATE_ROUTE)
    @PreAuthorize("hasAuthority('CREATE_TEAM')")
    @Validated
    public ResponseEntity<TeamDTO> updateTeamById(
            @PathVariable
            @NotNull
            @Positive
            long id,
            @RequestBody
            @Valid
            CreateTeamDTO updateTeam
    ) {

        TeamDTO team = teamService.updateTeamById(id, updateTeam);

        return ResponseEntity.ok(team);
    }

    @DeleteMapping(Route.API_DELETE_ROUTE)
    @PreAuthorize("hasAuthority('CREATE_TEAM')")
    @Validated
    public ResponseEntity<ResponseMessageDTO> deleteTeamById(
            @PathVariable
            @NotNull
            @Positive
            long id
    ) {

        teamService.deleteTeamById(id);

        return ResponseEntity.ok(new ResponseMessageDTO(ResponseMessage.SUCCESS_MESSAGE));
    }

    @PostMapping(Route.API_ADD_TEAMMATE_ROUTE)
    @PreAuthorize("hasAuthority('CREATE_TEAM')")
    @Validated
    public ResponseEntity<ResponseMessageDTO> addTeammateById(
            @PathVariable
            @NotNull
            @Positive
            long id,
            @RequestBody
            AddTeammateDTO teammate
    ) {

        teamService.addTeammateById(id, teammate);

        return ResponseEntity.ok(new ResponseMessageDTO(ResponseMessage.SUCCESS_MESSAGE));
    }

    @DeleteMapping(Route.API_DELETE_TEAMMATE_ROUTE)
    @PreAuthorize("hasAuthority('CREATE_TEAM')")
    @Validated
    public ResponseEntity<TeamDTO> deleteTeammateInTeamById(
            @PathVariable
            @NotNull
            @Positive
            long id,
            @PathVariable
            @NotNull
            @Positive
            long teammateId
    ) {

        TeamDTO updatedTeam = teamService.deleteTeammateInTeamById(id, teammateId);

        return ResponseEntity.ok(updatedTeam);
    }

    @PostMapping(Route.API_ADD_TEAMMATE_BY_LDAP_ROUTE)
    @PreAuthorize("hasAuthority('CREATE_TEAM')")
    @Validated
    public ResponseEntity<TeamDTO> addTeammateByLdap(
            @PathVariable
            @NotNull
            @Positive
            long id,
            @RequestBody
            AddTeammateByLdapDTO teammate
    ) {

        TeamDTO updatedTeam = teamService.addTeammateByLogin(id, teammate);

        return ResponseEntity.ok(updatedTeam);
    }
}
