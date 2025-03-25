package com.tests.test_case_helper.service.project.impl;

import com.tests.test_case_helper.dto.project.CreateProjectDTO;
import com.tests.test_case_helper.dto.project.CreateProjectResponseDTO;
import com.tests.test_case_helper.dto.project.ExtendedProjectDTO;
import com.tests.test_case_helper.dto.project.ProjectDTO;
import com.tests.test_case_helper.entity.Project;
import com.tests.test_case_helper.repository.ProjectRepository;
import com.tests.test_case_helper.service.project.ProjectService;
import com.tests.test_case_helper.service.project.utils.ProjectUtils;
import com.tests.test_case_helper.service.utils.ProjectMapper;
import com.tests.test_case_helper.service.utils.TestSuiteMapper;
import com.tests.test_case_helper.service.validation.manager.impl.ProjectValidationManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectValidationManager projectValidationManager;
    private final ProjectMapper projectMapper;
    private final ProjectUtils projectUtils;
    private final TestSuiteMapper testSuiteMapper;

    public ProjectServiceImpl(
            ProjectRepository projectRepository,
            ProjectValidationManager projectValidationManager,
            ProjectMapper projectMapper,
            ProjectUtils projectUtils,
            TestSuiteMapper testSuiteMapper) {
        this.projectRepository = projectRepository;
        this.projectValidationManager = projectValidationManager;
        this.projectMapper = projectMapper;
        this.projectUtils = projectUtils;
        this.testSuiteMapper = testSuiteMapper;
    }

    @Override
    public CreateProjectResponseDTO createProject(CreateProjectDTO createProjectDTO) {
        projectValidationManager.validate(createProjectDTO);

        Project project = projectMapper.toEntity(createProjectDTO);

        Project createdProject = projectRepository.save(project);

        return projectMapper.toDto(createdProject);
    }

    @Override
    public List<ProjectDTO> getAllProjects() {
        List<Project> projects = projectRepository.findAll();

        return projects.stream().map(projectMapper::currentProjectToDto).toList();
    }

    @Override
    public ExtendedProjectDTO getProject(Long projectId) {
        Project project = projectUtils.getProjectById(projectId);

        return new ExtendedProjectDTO(
                project.getTitle(),
                project.getDescription(),
                project.getTestsSuites().stream().map(testSuiteMapper::toBaseTestSuiteDTO).toList()
        );
    }

    @Override
    public void deleteProject(Long projectId) {
        Project project = projectUtils.getProjectById(projectId);

        projectRepository.delete(project);
    }

}
