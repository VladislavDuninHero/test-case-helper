package com.tests.test_case_helper.service.suite.impl;

import com.tests.test_case_helper.constants.ResponseMessage;
import com.tests.test_case_helper.dto.cases.TestCaseDTO;
import com.tests.test_case_helper.dto.message.ResponseMessageDTO;
import com.tests.test_case_helper.dto.suite.*;
import com.tests.test_case_helper.dto.suite.run.RunTestSuiteResponseDTO;
import com.tests.test_case_helper.entity.*;
import com.tests.test_case_helper.entity.cases.TestCase;
import com.tests.test_case_helper.enums.Environment;
import com.tests.test_case_helper.enums.Tag;
import com.tests.test_case_helper.enums.TestSuiteRunStatus;
import com.tests.test_case_helper.repository.TestCaseRepository;
import com.tests.test_case_helper.repository.TestSuiteRepository;
import com.tests.test_case_helper.repository.TestSuiteRunSessionRepository;
import com.tests.test_case_helper.service.cases.TestCaseService;
import com.tests.test_case_helper.service.cases.utils.impl.TestCaseUtil;
import com.tests.test_case_helper.service.project.utils.ProjectUtils;
import com.tests.test_case_helper.service.security.jwt.impl.JwtServiceImpl;
import com.tests.test_case_helper.service.suite.TestSuiteService;
import com.tests.test_case_helper.service.suite.run.TestSuiteRunSessionService;
import com.tests.test_case_helper.service.suite.run.TestSuiteRunSessionUtil;
import com.tests.test_case_helper.service.suite.utils.impl.TestSuiteUtil;
import com.tests.test_case_helper.service.user.UserUtils;
import com.tests.test_case_helper.service.utils.TestSuiteMapper;
import com.tests.test_case_helper.service.utils.cache.EvictService;
import com.tests.test_case_helper.service.utils.TestSuiteRunSessionMapper;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class TestSuiteServiceImpl implements TestSuiteService {

    private final TestSuiteRepository testSuiteRepository;
    private final ProjectUtils projectUtils;
    private final TestSuiteMapper testSuiteMapper;
    private final TestSuiteUtil testSuiteUtil;
    private final TestCaseService testCaseService;
    private final EvictService evictService;
    private final UserUtils userUtils;
    private final JwtServiceImpl jwtServiceImpl;
    private final TestSuiteRunSessionRepository testSuiteRunSessionRepository;
    private final TestCaseUtil testCaseUtil;
    private final TestCaseRepository testCaseRepository;
    private final TestSuiteRunSessionUtil testSuiteRunSessionService;
    private final TestSuiteRunSessionMapper testSuiteRunSessionMapper;

    public TestSuiteServiceImpl(
            TestSuiteRepository testSuiteRepository,
            ProjectUtils projectUtils,
            TestSuiteMapper testSuiteMapper,
            TestSuiteUtil testSuiteUtil,
            TestCaseService testCaseService,
            EvictService evictService
            TestCaseService testCaseService,
            UserUtils userUtils,
            JwtServiceImpl jwtServiceImpl,
            TestSuiteRunSessionRepository testSuiteRunSessionRepository,
            TestCaseUtil testCaseUtil,
            TestCaseRepository testCaseRepository,
            TestSuiteRunSessionUtil testSuiteRunSessionService,
            TestSuiteRunSessionMapper testSuiteRunSessionMapper
    ) {
        this.testSuiteRepository = testSuiteRepository;
        this.projectUtils = projectUtils;
        this.testSuiteMapper = testSuiteMapper;
        this.testSuiteUtil = testSuiteUtil;
        this.testCaseService = testCaseService;
        this.evictService = evictService;
        this.userUtils = userUtils;
        this.jwtServiceImpl = jwtServiceImpl;
        this.testSuiteRunSessionRepository = testSuiteRunSessionRepository;
        this.testCaseUtil = testCaseUtil;
        this.testCaseRepository = testCaseRepository;
        this.testSuiteRunSessionService = testSuiteRunSessionService;
        this.testSuiteRunSessionMapper = testSuiteRunSessionMapper;
    }

    @Override
    @CacheEvict(value = "project", key = "#createTestSuiteDTO.projectId")
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
        
        return createTestSuiteResponseDTO;
    }

    @Override
    @Transactional
    public TestSuiteDTO updateTestSuiteById(Long id, UpdateTestSuiteDTO updateTestSuiteDTO) {
        TestSuite foundTestSuite = testSuiteUtil.getTestSuiteById(id);

        evictService.evictProjectCache(foundTestSuite.getProject().getId());

        foundTestSuite.setTitle(updateTestSuiteDTO.getTitle());
        foundTestSuite.setDescription(updateTestSuiteDTO.getDescription());
        foundTestSuite.setTag(Tag.valueOf(updateTestSuiteDTO.getTag().toUpperCase()));

        TestSuite updatedTestSuite = testSuiteRepository.save(foundTestSuite);

        return testSuiteMapper.toBaseTestSuiteDTO(updatedTestSuite);
    }

    @Override
    public ExtendedTestSuiteDTO getTestSuite(Long id) {
        TestSuite testSuite = testSuiteUtil.getTestSuiteById(id);

        evictService.evictProjectCache(testSuite.getProject().getId());

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

        evictService.evictProjectCache(testSuite.getProject().getId());

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
    public void deleteTestSuite(Long id) {
        TestSuite testSuite = testSuiteUtil.getTestSuiteById(id);

        evictService.evictProjectCache(testSuite.getProject().getId());

        testSuiteRepository.delete(testSuite);
    }

    @Override
    @Transactional
    public ResponseMessageDTO runTestSuite(Long id, String env) {
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

        return new ResponseMessageDTO(ResponseMessage.SUCCESS_MESSAGE);
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
                .build();
    }

}
