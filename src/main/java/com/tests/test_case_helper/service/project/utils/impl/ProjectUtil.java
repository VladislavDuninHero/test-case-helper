package com.tests.test_case_helper.service.project.utils.impl;

import com.tests.test_case_helper.constants.ExceptionMessage;
import com.tests.test_case_helper.entity.Project;
import com.tests.test_case_helper.exceptions.ProjectNotFoundException;
import com.tests.test_case_helper.repository.ProjectRepository;
import com.tests.test_case_helper.service.project.utils.ProjectUtils;
import org.springframework.stereotype.Component;

@Component
public class ProjectUtil implements ProjectUtils {

    private final ProjectRepository projectRepository;

    public ProjectUtil(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project getProjectById(Long id) {
        return projectRepository.findProjectById(id)
                .orElseThrow(() -> new ProjectNotFoundException(ExceptionMessage.PROJECT_NOT_FOUND_EXCEPTION_MESSAGE));
    }

}
