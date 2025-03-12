package com.tests.test_case_helper.service.project;

import com.tests.test_case_helper.dto.project.CreateProjectDTO;
import com.tests.test_case_helper.dto.project.CreateProjectResponseDTO;

public interface ProjectService {
    CreateProjectResponseDTO createProject(CreateProjectDTO createProjectDTO);
}
