package com.tests.test_case_helper.service.project.impl;

import com.tests.test_case_helper.constants.ExceptionMessage;
import com.tests.test_case_helper.dto.project.*;
import com.tests.test_case_helper.dto.suite.run.RunTestSuiteSessionDTO;
import com.tests.test_case_helper.dto.teams.TeamSlimDTO;
import com.tests.test_case_helper.entity.*;
import com.tests.test_case_helper.entity.cases.TestCase;
import com.tests.test_case_helper.exceptions.ActiveTestingSessionIsExistsException;
import com.tests.test_case_helper.repository.ProjectRepository;
import com.tests.test_case_helper.repository.TestCaseRunResultsRepository;
import com.tests.test_case_helper.service.project.ProjectService;
import com.tests.test_case_helper.service.project.utils.ProjectUtils;
import com.tests.test_case_helper.service.suite.TestSuiteService;
import com.tests.test_case_helper.service.teams.utils.TeamUtils;
import com.tests.test_case_helper.service.user.UserUtils;
import com.tests.test_case_helper.service.utils.ProjectMapper;
import com.tests.test_case_helper.service.utils.TestSuiteMapper;
import com.tests.test_case_helper.service.utils.cache.EvictService;
import com.tests.test_case_helper.service.validation.manager.impl.ProjectValidationManager;
import jakarta.transaction.Transactional;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectValidationManager projectValidationManager;
    private final ProjectMapper projectMapper;
    private final ProjectUtils projectUtils;
    private final TestSuiteMapper testSuiteMapper;
    private final TestSuiteService testSuiteService;
    private final TeamUtils teamUtils;
    private final UserUtils userUtils;
    private final EvictService evictService;

    public ProjectServiceImpl(
            ProjectRepository projectRepository,
            ProjectValidationManager projectValidationManager,
            ProjectMapper projectMapper,
            ProjectUtils projectUtils,
            TestSuiteMapper testSuiteMapper,
            TestSuiteService testSuiteService,
            TeamUtils teamUtils,
            UserUtils userUtils,
            EvictService evictService
    ) {
        this.projectRepository = projectRepository;
        this.projectValidationManager = projectValidationManager;
        this.projectMapper = projectMapper;
        this.projectUtils = projectUtils;
        this.testSuiteMapper = testSuiteMapper;
        this.testSuiteService = testSuiteService;
        this.teamUtils = teamUtils;
        this.userUtils = userUtils;
        this.evictService = evictService;
    }

    @Override
    @Transactional
    @CacheEvict(value = {"user_projects", "project"}, keyGenerator = "userCacheKeyGeneratorService")
    public CreateProjectResponseDTO createProject(CreateProjectDTO createProjectDTO) {
        projectValidationManager.validate(createProjectDTO);

        Project project = projectMapper.toEntity(createProjectDTO);

        Team team = teamUtils.getTeamById(createProjectDTO.getTeamId());
        project.setTeam(team);

        evictService.evictTeamCache(project.getTeam().getTeammates());

        Project createdProject = projectRepository.save(project);

        return projectMapper.toDto(createdProject);
    }

    @Override
    @Cacheable(value = "user_projects", keyGenerator = "userCacheKeyGeneratorService")
    public List<ProjectTeamDTO> getAllProjects() {

        User user = userUtils.findUserBySecurityContextAndReturn();
        List<Long> userTeams = user.getTeams()
                .stream()
                .map(userTeam -> userTeam.getTeam().getId())
                .toList();

        return projectRepository.findAllByTeamIds(userTeams)
                .stream()
                .map(project -> new ProjectTeamDTO(
                        project.getId(),
                        project.getTitle(),
                        project.getTeamName(),
                        project.getDescription()
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "project", keyGenerator = "projectCacheKeyGenerator")
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
    @CacheEvict(value = {"user_projects", "project"}, keyGenerator = "userCacheKeyGeneratorService")
    public ProjectDTO updateProject(Long projectId, UpdateProjectDTO projectDTO) {
        Project foundProject = projectUtils.getProjectById(projectId);

        foundProject.setId(projectId);
        foundProject.setTitle(projectDTO.getTitle());
        foundProject.setDescription(projectDTO.getDescription());

        Project updatedProject = projectRepository.save(foundProject);

        evictService.evictTeamCache(foundProject.getTeam().getTeammates());

        return projectMapper.currentProjectToDto(updatedProject);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(
                    value = "project",
                    keyGenerator = "userCacheKeyGeneratorService"
            ),
            @CacheEvict(
                    value = "user_projects",
                    keyGenerator = "userCacheKeyGeneratorService"
            )
    })
    public void deleteProject(Long projectId) {
        Project project = projectUtils.getProjectById(projectId);

        List<RunTestSuiteSessionDTO> activeSessions = testSuiteService.getActiveRunTestSuiteSessionsByUser();
        if (!activeSessions.isEmpty()) {
            throw new ActiveTestingSessionIsExistsException(
                    ExceptionMessage.ACTIVE_TEST_SUITE_RUN_SESSION_IS_EXISTS_EXCEPTION_MESSAGE,
                    activeSessions
            );
        }

        evictService.evictTeamCache(project.getTeam().getTeammates());

        projectRepository.deleteProjectById(projectId);
    }

}
