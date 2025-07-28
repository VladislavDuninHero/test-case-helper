package com.tests.test_case_helper.service.suite.impl;

import com.tests.test_case_helper.constants.ExceptionMessage;
import com.tests.test_case_helper.constants.ResponseMessage;
import com.tests.test_case_helper.dto.cases.TestCaseDTO;
import com.tests.test_case_helper.dto.message.ResponseMessageDTO;
import com.tests.test_case_helper.dto.suite.*;
import com.tests.test_case_helper.dto.suite.run.*;
import com.tests.test_case_helper.dto.suite.run.cases.TestCaseRunResultDTO;
import com.tests.test_case_helper.dto.suite.run.cases.TestCaseRunResultSlimDTO;
import com.tests.test_case_helper.dto.suite.run.cases.TestSuiteRunSessionResultsDTO;
import com.tests.test_case_helper.entity.*;
import com.tests.test_case_helper.entity.cases.TestCase;
import com.tests.test_case_helper.enums.Environment;
import com.tests.test_case_helper.enums.Tag;
import com.tests.test_case_helper.enums.TestCaseStatus;
import com.tests.test_case_helper.enums.TestSuiteRunStatus;
import com.tests.test_case_helper.exceptions.ActiveTestingSessionIsExistsException;
import com.tests.test_case_helper.exceptions.TestSuiteRunSessionAlreadyEndedException;
import com.tests.test_case_helper.exceptions.TestSuiteRunSessionNotEndedException;
import com.tests.test_case_helper.exceptions.TestSuiteRunSessionNotFoundException;
import com.tests.test_case_helper.repository.TestCaseRepository;
import com.tests.test_case_helper.repository.TestCaseRunResultsRepository;
import com.tests.test_case_helper.repository.TestSuiteRepository;
import com.tests.test_case_helper.repository.TestSuiteRunSessionRepository;
import com.tests.test_case_helper.repository.projections.TestCaseRunResultSlimProjection;
import com.tests.test_case_helper.repository.projections.TestSuiteRunSessionProjection;
import com.tests.test_case_helper.service.cases.TestCaseService;
import com.tests.test_case_helper.service.cases.utils.impl.TestCaseUtil;
import com.tests.test_case_helper.service.project.utils.ProjectUtils;
import com.tests.test_case_helper.service.security.jwt.impl.JwtServiceImpl;
import com.tests.test_case_helper.service.suite.TestSuiteService;
import com.tests.test_case_helper.service.suite.run.TestSuiteRunSessionUtil;
import com.tests.test_case_helper.service.suite.run.result.TestCaseResultUtils;
import com.tests.test_case_helper.service.suite.run.result.TestCaseRunResultService;
import com.tests.test_case_helper.service.suite.utils.impl.TestSuiteUtil;
import com.tests.test_case_helper.service.user.UserUtils;
import com.tests.test_case_helper.service.utils.TestSuiteMapper;
import com.tests.test_case_helper.service.utils.TestSuiteRunResultMapper;
import com.tests.test_case_helper.service.utils.UserMapper;
import com.tests.test_case_helper.service.utils.cache.EvictService;
import com.tests.test_case_helper.service.utils.TestSuiteRunSessionMapper;
import com.tests.test_case_helper.service.validation.manager.impl.TestSuiteRunSessionValidationManager;
import jakarta.transaction.Transactional;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TestSuiteServiceImpl implements TestSuiteService {

    private final TestSuiteRepository testSuiteRepository;
    private final ProjectUtils projectUtils;
    private final TestSuiteMapper testSuiteMapper;
    private final TestSuiteUtil testSuiteUtil;
    private final EvictService evictService;
    private final UserUtils userUtils;
    private final JwtServiceImpl jwtServiceImpl;
    private final TestSuiteRunSessionRepository testSuiteRunSessionRepository;
    private final TestCaseUtil testCaseUtil;
    private final TestCaseRepository testCaseRepository;
    private final TestSuiteRunSessionUtil testSuiteRunSessionService;
    private final TestSuiteRunSessionMapper testSuiteRunSessionMapper;
    private final TestCaseService testCaseService;
    private final TestCaseRunResultsRepository testCaseRunResultsRepository;
    private final TestSuiteRunResultMapper testSuiteRunResultMapper;
    private final TestCaseResultUtils testCaseResultService;
    private final TestCaseRunResultService testCaseRunResultService;
    private final TestSuiteRunSessionUtil testSuiteRunSessionUtil;
    private final UserMapper userMapper;
    private final TestSuiteRunSessionValidationManager testSuiteRunSessionValidationManager;

    public TestSuiteServiceImpl(
            TestSuiteRepository testSuiteRepository,
            ProjectUtils projectUtils,
            TestSuiteMapper testSuiteMapper,
            TestSuiteUtil testSuiteUtil,
            TestCaseService testCaseService,
            EvictService evictService,
            UserUtils userUtils,
            JwtServiceImpl jwtServiceImpl,
            TestSuiteRunSessionRepository testSuiteRunSessionRepository,
            TestCaseUtil testCaseUtil,
            TestCaseRepository testCaseRepository,
            TestSuiteRunSessionUtil testSuiteRunSessionService,
            TestSuiteRunSessionMapper testSuiteRunSessionMapper,
            TestCaseRunResultsRepository testCaseRunResultsRepository,
            TestSuiteRunResultMapper testSuiteRunResultMapper,
            TestCaseResultUtils testCaseResultService,
            TestCaseRunResultService testCaseRunResultService,
            TestSuiteRunSessionUtil testSuiteRunSessionUtil,
            UserMapper userMapper,
            TestSuiteRunSessionValidationManager testSuiteRunSessionValidationManager
    ) {
        this.testSuiteRepository = testSuiteRepository;
        this.projectUtils = projectUtils;
        this.testSuiteMapper = testSuiteMapper;
        this.testSuiteUtil = testSuiteUtil;
        this.evictService = evictService;
        this.userUtils = userUtils;
        this.jwtServiceImpl = jwtServiceImpl;
        this.testSuiteRunSessionRepository = testSuiteRunSessionRepository;
        this.testCaseUtil = testCaseUtil;
        this.testCaseRepository = testCaseRepository;
        this.testSuiteRunSessionService = testSuiteRunSessionService;
        this.testSuiteRunSessionMapper = testSuiteRunSessionMapper;
        this.testCaseService = testCaseService;
        this.testCaseRunResultsRepository = testCaseRunResultsRepository;
        this.testSuiteRunResultMapper = testSuiteRunResultMapper;
        this.testCaseResultService = testCaseResultService;
        this.testCaseRunResultService = testCaseRunResultService;
        this.testSuiteRunSessionUtil = testSuiteRunSessionUtil;
        this.userMapper = userMapper;
        this.testSuiteRunSessionValidationManager = testSuiteRunSessionValidationManager;
    }

    @Override
    @Transactional
    @CacheEvict(value = "project", keyGenerator = "userCacheKeyGeneratorService")
    public CreateTestSuiteResponseDTO createTestSuite(CreateTestSuiteDTO createTestSuiteDTO) {
        Project project = projectUtils.getProjectById(createTestSuiteDTO.getProjectId());

        TestSuite testSuite = TestSuite.builder()
                .title(createTestSuiteDTO.getTitle())
                .description(createTestSuiteDTO.getDescription())
                .project(project)
                .tag(Tag.valueOf(createTestSuiteDTO.getTag()))
                .build();

        TestSuite createdTestSuit = testSuiteRepository.save(testSuite);

        CreateTestSuiteResponseDTO createTestSuiteResponseDTO = testSuiteMapper.toDto(createdTestSuit);
        createTestSuiteResponseDTO.setProjectId(project.getId());

        evictService.evictTeamCache(project.getTeam().getTeammates());

        return createTestSuiteResponseDTO;
    }

    @Override
    @Transactional
    @CacheEvict(value = "project", keyGenerator = "userCacheKeyGeneratorService")
    public TestSuiteDTO updateTestSuiteById(Long id, UpdateTestSuiteDTO updateTestSuiteDTO) {
        TestSuite foundTestSuite = testSuiteUtil.getTestSuiteById(id);

        evictService.evictTeamCache(foundTestSuite.getProject().getTeam().getTeammates());

        foundTestSuite.setTitle(updateTestSuiteDTO.getTitle());
        foundTestSuite.setDescription(updateTestSuiteDTO.getDescription());
        foundTestSuite.setTag(Tag.valueOf(updateTestSuiteDTO.getTag().toUpperCase()));

        TestSuite updatedTestSuite = testSuiteRepository.save(foundTestSuite);

        return testSuiteMapper.toBaseTestSuiteDTO(updatedTestSuite);
    }

    @Override
    public ExtendedTestSuiteDTO getTestSuite(Long id) {
        TestSuite testSuite = testSuiteUtil.getTestSuiteById(id);

        List<TestCaseDTO> testCases = testCaseService.getTestCasesByTestSuiteId(id);

        return ExtendedTestSuiteDTO.builder()
                .title(testSuite.getTitle())
                .description(testSuite.getDescription())
                .projectId(testSuite.getProject().getId())
                .numberOfTestCases(testSuite.getTestsCases().size())
                .testCases(testCases)
                .build();
    }

    @Override
    public ExtendedTestSuiteDTO getTestSuite(Long id, Pageable pageable) {
        TestSuite testSuite = testSuiteUtil.getTestSuiteById(id);

        List<TestCaseDTO> testCases = testCaseService.getTestCasesByTestSuiteId(id, pageable);

        return ExtendedTestSuiteDTO.builder()
                .title(testSuite.getTitle())
                .description(testSuite.getDescription())
                .projectId(testSuite.getProject().getId())
                .numberOfTestCases(testSuite.getTestsCases().size())
                .testCases(testCases)
                .build();
    }

    @Override
    @Transactional
    @CacheEvict(value = "project", keyGenerator = "userCacheKeyGeneratorService")
    public void deleteTestSuite(Long id) {
        TestSuite testSuite = testSuiteUtil.getTestSuiteById(id);
        User user = userUtils.findUserBySecurityContextAndReturn();

        List<RunTestSuiteSessionDTO> activeSessions = getActiveRunTestSuiteSessionsByUserId(
                user.getId(), testSuite.getId()
        );
        if (!activeSessions.isEmpty()) {
            throw new ActiveTestingSessionIsExistsException(
                    ExceptionMessage.ACTIVE_TEST_SUITE_RUN_SESSION_IS_EXISTS_EXCEPTION_MESSAGE,
                    activeSessions
            );
        }

        evictService.evictTeamCache(testSuite.getProject().getTeam().getTeammates());

        testSuiteRepository.delete(testSuite);
    }

    @Override
    @Transactional
    @CacheEvict(value = "project", keyGenerator = "userCacheKeyGeneratorService")
    public RunTestSuiteSessionResponseDTO runTestSuite(Long id, String env) {
        TestSuite testSuite = testSuiteUtil.getTestSuiteById(id);
        User user = userUtils.findUserEntityByLoginAndReturn(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        List<TestCase> testCases = testCaseRepository.getAllTestCasesByTestSuiteIdWithoutPageable(id);

        TestSuiteRunSession session = TestSuiteRunSession
                .builder()
                .testSuite(testSuite)
                .startTime(LocalDateTime.now())
                .environment(Environment.valueOf(env.toUpperCase()))
                .executedBy(user)
                .status(TestSuiteRunStatus.IN_PROGRESS)
                .build();
        testSuiteRunSessionRepository.save(session);

        List<TestCaseRunResult> runResults = testSuiteRunSessionService.createRunResults(testCases, session);

        session.setTestCaseRunResults(runResults);
        testSuiteRunSessionRepository.save(session);

        return RunTestSuiteSessionResponseDTO
                .builder()
                .runSessionId(session.getId())
                .message(ResponseMessage.SUCCESS_MESSAGE)
                .build();
    }

    @Override
    public RunTestSuiteResponseDTO getRunTestSuiteSessionById(Long id, Long sessionId, Pageable pageable) {
        return testSuiteRunSessionService.getRunTestSuiteSessionById(id, sessionId, pageable);
    }

    @Override
    public TestSuiteDTO getSlimTestSuite(Long id) {
        TestSuite testSuite = testSuiteUtil.getTestSuiteById(id);

        return TestSuiteDTO.builder()
                .id(testSuite.getId())
                .title(testSuite.getTitle())
                .description(testSuite.getDescription())
                .tag(testSuite.getTag().name())
                .numberOfTestCases(testSuite.getTestsCases().size())
                .build();
    }

    @Override
    public List<RunTestSuiteSessionDTO> getActiveRunTestSuiteSessions() {
        User user = userUtils.findUserEntityByLoginAndReturn(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );

        List<TestSuiteRunSessionRepository
                .TestSuiteRunSessionSlimProjection> testSuiteActiveRunsProjection = testSuiteRunSessionRepository
                .getTestSuiteRunSessionsSlimByUserId(user.getId());

        return testSuiteActiveRunsProjection
                .stream()
                .map(activeTestSuite -> new RunTestSuiteSessionDTO(
                        activeTestSuite.getId(),
                        activeTestSuite.getTestSuite().getId(),
                        activeTestSuite.getTestSuite().getTitle(),
                        activeTestSuite.getEnvironment(),
                        activeTestSuite.getStartTime()
                ))
                .toList();
    }

    @Override
    public List<RunTestSuiteSessionDTO> getActiveRunTestSuiteSessionsByScheduled() {
        List<TestSuiteRunSessionRepository
                .TestSuiteRunSessionSlimProjection> testSuiteActiveRunsProjection = testSuiteRunSessionRepository
                .getActiveTestSuiteRunSessions();

        return testSuiteActiveRunsProjection
                .stream()
                .map(activeTestSuite -> new RunTestSuiteSessionDTO(
                        activeTestSuite.getId(),
                        activeTestSuite.getTestSuite().getId(),
                        activeTestSuite.getTestSuite().getTitle(),
                        activeTestSuite.getEnvironment(),
                        activeTestSuite.getStartTime()
                ))
                .toList();
    }

    @Override
    @CacheEvict(value = "project", keyGenerator = "userCacheKeyGeneratorService")
    public ResponseMessageDTO deleteRunTestSuiteSessionById(Long id, Long sessionId) {
        TestSuiteRunSession session = testSuiteRunSessionRepository.findById(sessionId)
                .orElseThrow(() -> new TestSuiteRunSessionNotFoundException(
                        ExceptionMessage.TEST_SUITE_RUN_SESSION_NOT_FOUND_EXCEPTION_MESSAGE)
                );

        testSuiteRunSessionRepository.delete(session);

        return new ResponseMessageDTO(ResponseMessage.SUCCESS_MESSAGE);
    }

    @Override
    @CacheEvict(value = "project", keyGenerator = "userCacheKeyGeneratorService")
    public UpdateTestCaseResultResponseDTO updateTestCaseResultInTestSuiteSessionById(UpdateTestCaseResultDTO updateTestCaseResultDTO) {
        testSuiteUtil.getTestSuiteById(updateTestCaseResultDTO.getTestSuiteId());
        testSuiteRunSessionService.findTestSuiteRunSessionById(
                updateTestCaseResultDTO.getTestSuiteId(), updateTestCaseResultDTO.getSessionId()
        );
        TestCaseRunResult testCaseRunResult = testCaseResultService.getTestCaseResultInTestSuiteById(
                updateTestCaseResultDTO.getTestCaseResultId()
        );

        testCaseRunResult.setActualResult(updateTestCaseResultDTO.getActualResult());
        testCaseRunResult.setStatus(TestCaseStatus.valueOf(updateTestCaseResultDTO.getStatus().toUpperCase()));
        testCaseRunResult.setComment(updateTestCaseResultDTO.getComment());

        TestCaseRunResult updatedResult = testCaseRunResultsRepository.save(testCaseRunResult);

        List<TestSuiteRunSessionStatisticDTO> updatedStatistic = testCaseRunResultService
                .getStatusStatisticsBySessionId(updateTestCaseResultDTO.getSessionId());

        return UpdateTestCaseResultResponseDTO.builder()
                .testCaseResultId(updatedResult.getId())
                .actualResult(updatedResult.getActualResult())
                .status(updatedResult.getStatus())
                .comment(updatedResult.getComment())
                .sessionStatistic(updatedStatistic)
                .build();
    }

    @Override
    public TestCaseRunResultDTO getTestCaseResultInTestSuiteById(Long id) {
        return testSuiteRunResultMapper.toDto(testCaseResultService.getTestCaseResultInTestSuiteById(id));
    }

    @Override
    public List<RunTestSuiteSessionDTO> getActiveRunTestSuiteSessionsByUserId(Long userId, Long testSuiteId) {

        List<RunTestSuiteSessionDTO> activeSessions = getActiveRunTestSuiteSessions();

        return activeSessions.stream()
                .filter(active -> active.getTestSuiteId().equals(testSuiteId))
                .collect(Collectors.toList());
    }

    @Override
    public List<RunTestSuiteSessionDTO> getActiveRunTestSuiteSessionsByUser() {
        return getActiveRunTestSuiteSessions();
    }

    @Override
    @Transactional
    @CacheEvict(value = "project", keyGenerator = "userCacheKeyGeneratorService")
    public ResponseMessageDTO endTestSuiteRunSessionById(Long id, TestSuiteRunStatus status) {
        TestSuiteRunSessionProjection session = testSuiteRunSessionService.findTestSuiteRunSessionByIdAndReturn(id);

        if (
                session.getStatus().equals(TestSuiteRunStatus.ENDED.name())
                || session.getStatus().equals(TestSuiteRunStatus.ENDED_BY_TIMEOUT.name())
        ) {
            throw new TestSuiteRunSessionAlreadyEndedException(ExceptionMessage.TEST_SUITE_RUN_SESSION_ALREADY_ENDED_EXCEPTION_MESSAGE);
        }

        testSuiteRunSessionRepository.endTestSuiteRunSessionById(
                id,
                LocalDateTime.now(),
                status
        );

        return new ResponseMessageDTO(ResponseMessage.SUCCESS_MESSAGE);
    }

    public EndTestSuiteRunSessionDTO getEndedTestSuiteRunSessionById(Long id, TestSuiteRunStatus status) {
        TestSuiteRunSessionProjection session = testSuiteRunSessionService.findTestSuiteRunSessionByIdAndReturn(id);

        if (
                !session.getStatus().equals(TestSuiteRunStatus.ENDED.name())
                        && !session.getStatus().equals(TestSuiteRunStatus.ENDED_BY_TIMEOUT.name())
        ) {
            throw new TestSuiteRunSessionNotEndedException(
                    ExceptionMessage.TEST_SUITE_RUN_SESSION_NOT_ENDED_EXCEPTION_MESSAGE
            );
        }

        Long totalTime = testSuiteRunSessionService.calculateRunTestSuiteSessionExecutionTime(session);
        List<TestSuiteRunSessionStatisticDTO> statistic = testCaseResultService.getStatusStatisticsBySessionId(id);

        return EndTestSuiteRunSessionDTO.builder()
                .id(session.getId())
                .testSuiteTitle(session.getTestSuiteTitle())
                .environment(session.getEnvironment())
                .executedBy(userMapper.toUserDTO(session.getExecutedBy()))
                .executionTime(totalTime)
                .sessionStatistic(statistic)
                .build();
    }

    @Override
    public TestSuiteRunSessionResultsDTO getEndedTestSuiteRunSessionResultsById(Long id, Pageable pageable) {
        TestSuiteRunSessionProjection session = testSuiteRunSessionService.findTestSuiteRunSessionByIdAndReturn(id);

        testSuiteRunSessionService.validateUserInTestSuiteRunSession(session);

        List<TestCaseRunResultSlimProjection> results = testCaseRunResultsRepository
                .findAllTestCaseRunResultsSlimById(id, pageable);

        Integer allResultsSize = testCaseRunResultsRepository.countAllResultsForSession(id);

        List<TestCaseRunResultSlimDTO> mappedResults = results.stream()
                .map(result -> new TestCaseRunResultSlimDTO(
                            result.getId(), result.getTestCaseTitle(), result.getStatus()
                        )
                )
                .toList();

        return new TestSuiteRunSessionResultsDTO(
                allResultsSize,
                mappedResults
        );
    }

}
