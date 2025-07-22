package com.tests.test_case_helper.service.utils.scheduled.daily.impl;

import com.tests.test_case_helper.dto.suite.run.RunTestSuiteSessionDTO;
import com.tests.test_case_helper.enums.TestSuiteRunStatus;
import com.tests.test_case_helper.service.suite.TestSuiteService;
import com.tests.test_case_helper.service.utils.scheduled.daily.DailyTasksService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DailyTasksManager implements DailyTasksService {

    private final TestSuiteService testSuiteService;

    public DailyTasksManager(
            TestSuiteService testSuiteService
    ) {
        this.testSuiteService = testSuiteService;
    }

    @Override
    public void endAllActiveTestSuiteRunSessions() {
        List<RunTestSuiteSessionDTO> activeSessions = testSuiteService.getActiveRunTestSuiteSessionsByScheduled();

        activeSessions.forEach(session -> {
                Duration duration = Duration.between(session.getStartTime(), LocalDateTime.now());
                long totalWorkingHours = (duration.toHours() / 24) * 8;

                if (totalWorkingHours >= 40) {
                    testSuiteService.endTestSuiteRunSessionById(
                            session.getRunSessionId(),
                            TestSuiteRunStatus.ENDED_BY_TIMEOUT
                    );
                }
            }
        );
    }
}
