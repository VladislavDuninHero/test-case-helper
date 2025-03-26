package com.tests.test_case_helper.service.project;

import com.tests.test_case_helper.dto.project.*;

import java.util.List;

public interface ProjectService {
    CreateProjectResponseDTO createProject(CreateProjectDTO createProjectDTO);
    List<ProjectDTO> getAllProjects();
    ExtendedProjectDTO getProject(Long projectId);
    ProjectDTO updateProject(Long projectId, UpdateProjectDTO projectDTO);
    void deleteProject(Long projectId);
}
