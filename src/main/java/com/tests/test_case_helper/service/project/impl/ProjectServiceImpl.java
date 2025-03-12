package com.tests.test_case_helper.service.project.impl;

import com.tests.test_case_helper.dto.project.CreateProjectDTO;
import com.tests.test_case_helper.dto.project.CreateProjectResponseDTO;
import com.tests.test_case_helper.repository.ProjectRepository;
import com.tests.test_case_helper.service.project.ProjectService;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public CreateProjectResponseDTO createProject(CreateProjectDTO createProjectDTO) {



        return null;
    }

}
