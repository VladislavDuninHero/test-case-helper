package com.tests.test_case_helper.service.utils;

import com.tests.test_case_helper.dto.teams.TeamDTO;
import com.tests.test_case_helper.entity.Team;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    Team toEntity(TeamDTO team);
    TeamDTO toDto(Team team);
}
