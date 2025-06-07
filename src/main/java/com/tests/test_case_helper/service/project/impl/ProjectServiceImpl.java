package com.tests.test_case_helper.service.project.impl;

import com.tests.test_case_helper.dto.project.*;
import com.tests.test_case_helper.entity.Project;
import com.tests.test_case_helper.repository.ProjectRepository;
import com.tests.test_case_helper.service.project.ProjectService;
import com.tests.test_case_helper.service.project.utils.ProjectUtils;
import com.tests.test_case_helper.service.utils.ProjectMapper;
import com.tests.test_case_helper.service.utils.TestSuiteMapper;
import com.tests.test_case_helper.service.validation.manager.impl.ProjectValidationManager;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableCaching
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
            TestSuiteMapper testSuiteMapper
    ) {
        this.projectRepository = projectRepository;
        this.projectValidationManager = projectValidationManager;
        this.projectMapper = projectMapper;
        this.projectUtils = projectUtils;
        this.testSuiteMapper = testSuiteMapper;
    }

    @Override
    @CacheEvict(value = "projects", allEntries = true)
    public CreateProjectResponseDTO createProject(CreateProjectDTO createProjectDTO) {
        projectValidationManager.validate(createProjectDTO);

        Project project = projectMapper.toEntity(createProjectDTO);

        Project createdProject = projectRepository.save(project);

        return projectMapper.toDto(createdProject);
    }

    @Override
    @Cacheable(value = "projects", key = "'all_projects'")
    public List<ProjectDTO> getAllProjects() {
        List<Project> projects = projectRepository.findAll();

        return projects.stream().map(projectMapper::currentProjectToDto).toList();
    }

    @Override
    @Cacheable(value = "project", key = "#projectId")
    public ExtendedProjectDTO getProject(Long projectId) {
        Project project = projectUtils.getProjectById(projectId);

        return new ExtendedProjectDTO(
                project.getTitle(),
                project.getDescription(),
                project.getTestsSuites().stream().map(testSuiteMapper::toBaseTestSuiteDTO).toList()
        );
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(
                    value = "project",
                    key = "#projectId",
                    allEntries = true,
                    condition = "#projectDTO.title != null || #projectDTO.description != null"
            ),
            @CacheEvict(
                    value = "projects",
                    allEntries = true,
                    condition = "#projectDTO.title != null || #projectDTO.description != null"
            )
    })
    public ProjectDTO updateProject(Long projectId, UpdateProjectDTO projectDTO) {
        Project foundProject = projectUtils.getProjectById(projectId);

        foundProject.setId(projectId);
        foundProject.setTitle(projectDTO.getTitle());
        foundProject.setDescription(projectDTO.getDescription());

        Project updatedProject = projectRepository.save(foundProject);

        return projectMapper.currentProjectToDto(updatedProject);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "project", key = "#projectId"),
            @CacheEvict(value = "projects", allEntries = true)
    })
    public void deleteProject(Long projectId) {
        Project project = projectUtils.getProjectById(projectId);

        projectRepository.delete(project);
    }

}
