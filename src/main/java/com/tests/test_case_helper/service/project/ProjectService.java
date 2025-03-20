package com.tests.test_case_helper.service.project;

import com.tests.test_case_helper.dto.project.CreateProjectDTO;
import com.tests.test_case_helper.dto.project.CreateProjectResponseDTO;
import com.tests.test_case_helper.dto.project.ExtendedProjectDTO;
import com.tests.test_case_helper.dto.project.ProjectDTO;

import java.util.List;

public interface ProjectService {
    CreateProjectResponseDTO createProject(CreateProjectDTO createProjectDTO);
    List<ProjectDTO> getAllProjects();
    ExtendedProjectDTO getProject(Long projectId);
}
