package com.tests.test_case_helper.service.suite.run;

import com.tests.test_case_helper.constants.ExceptionMessage;
import com.tests.test_case_helper.dto.suite.run.RunTestSuiteResponseDTO;
import com.tests.test_case_helper.dto.suite.run.cases.TestCaseRunResultDTO;
import com.tests.test_case_helper.dto.suite.run.TestSuiteRunSessionStatisticDTO;
import com.tests.test_case_helper.entity.TestCaseRunResult;
import com.tests.test_case_helper.entity.TestSuiteRunSession;
import com.tests.test_case_helper.entity.User;
import com.tests.test_case_helper.entity.cases.TestCase;
import com.tests.test_case_helper.enums.TestCaseStatus;
import com.tests.test_case_helper.enums.TestSuiteRunStatus;
import com.tests.test_case_helper.exceptions.TestSuiteRunSessionNotFoundException;
import com.tests.test_case_helper.repository.TestCaseRunResultsRepository;
import com.tests.test_case_helper.repository.TestSuiteRepository;
import com.tests.test_case_helper.repository.TestSuiteRunSessionRepository;
import com.tests.test_case_helper.repository.projections.TestSuiteRunSessionProjection;
import com.tests.test_case_helper.service.suite.run.result.TestCaseResultUtils;
import com.tests.test_case_helper.service.suite.run.result.TestCaseRunResultService;
import com.tests.test_case_helper.service.user.UserUtils;
import com.tests.test_case_helper.service.utils.TestSuiteMapper;
import com.tests.test_case_helper.service.utils.TestSuiteRunResultMapper;
import com.tests.test_case_helper.service.utils.TestSuiteRunSessionMapper;
import com.tests.test_case_helper.service.utils.UserMapper;
import com.tests.test_case_helper.service.validation.manager.impl.TestSuiteRunSessionValidationManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestSuiteRunSessionService implements TestSuiteRunSessionUtil {

    private final TestSuiteRunSessionMapper testSuiteRunSessionMapper;
    private final TestCaseRunResultsRepository testCaseRunResultsRepository;
    private final TestSuiteRunResultMapper testSuiteRunResultMapper;
    private final TestSuiteRunSessionRepository testSuiteRunSessionRepository;
    private final TestSuiteRepository testSuiteRepository;
    private final TestSuiteMapper testSuiteMapper;
    private final UserMapper userMapper;
    private final UserUtils userUtils;
    private final TestCaseResultUtils testCaseResultService;
    private final TestCaseRunResultService testCaseRunResultService;
    private final TestSuiteRunSessionValidationManager testSuiteRunSessionValidationManager;

    public TestSuiteRunSessionService(
            TestSuiteRunSessionMapper testSuiteRunSessionMapper,
            TestCaseRunResultsRepository testCaseRunResultsRepository,
            TestSuiteRunResultMapper testSuiteRunResultMapper,
            TestSuiteRunSessionRepository testSuiteRunSessionRepository,
            TestSuiteRepository testSuiteRepository,
            TestSuiteMapper testSuiteMapper,
            UserMapper userMapper,
            UserUtils userUtils,
            TestCaseResultUtils testCaseResultService,
            TestCaseRunResultService testCaseRunResultService,
            TestSuiteRunSessionValidationManager testSuiteRunSessionValidationManager
    ) {
        this.testSuiteRunSessionMapper = testSuiteRunSessionMapper;
        this.testCaseRunResultsRepository = testCaseRunResultsRepository;
        this.testSuiteRunResultMapper = testSuiteRunResultMapper;
        this.testSuiteRunSessionRepository = testSuiteRunSessionRepository;
        this.testSuiteRepository = testSuiteRepository;
        this.testSuiteMapper = testSuiteMapper;
        this.userMapper = userMapper;
        this.userUtils = userUtils;
        this.testCaseResultService = testCaseResultService;
        this.testCaseRunResultService = testCaseRunResultService;
        this.testSuiteRunSessionValidationManager = testSuiteRunSessionValidationManager;
    }

    @Override
    public List<TestCaseRunResult> createRunResults(List<TestCase> testCases, TestSuiteRunSession runSession) {
        return testCases.stream()
                .map(tc -> createRunResult(tc, runSession))
                .collect(Collectors.toList());
    }

    @Override
    public RunTestSuiteResponseDTO mapSessionToDto(TestSuiteRunSession session) {
        return testSuiteRunSessionMapper.toDto(session);
    }

    @Override
    public RunTestSuiteResponseDTO getRunTestSuiteSessionById(Long id, Long sessionId, Pageable pageable) {

        List<TestCaseRunResult> testCaseRunningResults = testCaseRunResultsRepository
                .findAllTestCaseResultsByRunSessionId(id, sessionId, pageable);

        List<TestCaseRunResultDTO> mappedResults = testCaseRunningResults.stream()
                .map(testSuiteRunResultMapper::toDto)
                .collect(Collectors.toList());

        TestSuiteRunSessionRepository.TestSuiteRunSessionSlimProjection session = testSuiteRunSessionRepository
                .getTestSuiteRunSessionSlimById(id, sessionId)
                .orElseThrow(() -> new TestSuiteRunSessionNotFoundException(
                            ExceptionMessage.TEST_SUITE_RUN_SESSION_NOT_FOUND_EXCEPTION_MESSAGE
                        )
                );

        testSuiteRunSessionValidationManager.validate(session);

        List<TestSuiteRunSessionStatisticDTO> sessionStatistic = testCaseRunResultService
                .getStatusStatisticsBySessionId(sessionId);

        return RunTestSuiteResponseDTO.builder()
                .id(session.getId())
                .testSuite(testSuiteMapper.toBaseTestSuiteDTO(session.getTestSuite()))
                .executedBy(userMapper.toUserDTO(session.getExecutedBy()))
                .environment(session.getEnvironment())
                .testCaseRunResults(mappedResults)
                .sessionStatistic(sessionStatistic)
                .status(TestSuiteRunStatus.valueOf(session.getStatus()))
                .build();
    }

    @Override
    public void findTestSuiteRunSessionById(Long id, Long sessionId) {
        testSuiteRunSessionRepository.getTestSuiteRunSessionSlimById(id, sessionId)
                .orElseThrow(() -> new TestSuiteRunSessionNotFoundException(
                        ExceptionMessage.TEST_SUITE_RUN_SESSION_NOT_FOUND_EXCEPTION_MESSAGE
                    )
                );
    }

    @Override
    public TestSuiteRunSessionProjection findTestSuiteRunSessionByIdAndReturn(Long id) {
        return testSuiteRunSessionRepository.getTestSuiteRunSessionSlimById(id)
                .orElseThrow(() -> new TestSuiteRunSessionNotFoundException(
                                ExceptionMessage.TEST_SUITE_RUN_SESSION_NOT_FOUND_EXCEPTION_MESSAGE
                        )
                );
    }

    @Override
    public Long calculateRunTestSuiteSessionExecutionTime(TestSuiteRunSessionProjection runSession) {
        LocalDateTime currentTime = runSession.getEndTime() != null ? runSession.getEndTime() : LocalDateTime.now();
        LocalDateTime startSessionTime = runSession.getStartTime();

        Duration duration = Duration.between(startSessionTime, currentTime);

        return duration.getSeconds();
    }

    @Override
    public String calculateRunTestSuiteSessionExecutionTimeForWord(TestSuiteRunSessionProjection runSession) {
        LocalDateTime startSessionTime = runSession.getStartTime();
        LocalDateTime endTime = runSession.getEndTime();

        Duration duration = Duration.between(startSessionTime, endTime);

        long totalSeconds = duration.getSeconds();
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;

        return String.format("%02d ч. %02d м.", hours, minutes);
    }

    @Override
    public void validateUserInTestSuiteRunSession(TestSuiteRunSessionProjection runSession) {
        User user = userUtils.findUserBySecurityContextAndReturn();

        if (!runSession.getExecutedBy().equals(user)) {
            throw new TestSuiteRunSessionNotFoundException(
                    ExceptionMessage.TEST_SUITE_RUN_SESSION_NOT_FOUND_EXCEPTION_MESSAGE
            );
        }
    }

    private TestCaseRunResult createRunResult(TestCase testCase, TestSuiteRunSession runSession) {
        return TestCaseRunResult.builder()
                .testSuiteRunSession(runSession)
                .testCase(testCase)
                .actualResult("")
                .status(TestCaseStatus.NOT_TESTING)
                .comment("")
                .build();
    }
}
