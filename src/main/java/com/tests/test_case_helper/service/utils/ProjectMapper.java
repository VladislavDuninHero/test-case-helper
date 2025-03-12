package com.tests.test_case_helper.service.utils;

import com.tests.test_case_helper.dto.project.CreateProjectDTO;
import com.tests.test_case_helper.dto.project.CreateProjectResponseDTO;
import com.tests.test_case_helper.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(source = "title", target = "title")
    Project toEntity(CreateProjectDTO project);

    @Mapping(source = "title", target = "title")
    CreateProjectResponseDTO toDto(Project project);
}
