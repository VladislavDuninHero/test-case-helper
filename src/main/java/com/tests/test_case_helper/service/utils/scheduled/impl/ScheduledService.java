package com.tests.test_case_helper.service.utils.scheduled.impl;

import com.tests.test_case_helper.service.utils.scheduled.ScheduledUtils;
import com.tests.test_case_helper.service.utils.scheduled.daily.DailyTasksService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class ScheduledService implements ScheduledUtils {

    private final DailyTasksService dailyTasksService;

    public ScheduledService(
            DailyTasksService dailyTasksService
    ) {
        this.dailyTasksService = dailyTasksService;
    }

    @Override
    @Scheduled(fixedRate = 30 * 60 * 1000)
    public void runDailyTasks() {
        dailyTasksService.endAllActiveTestSuiteRunSessions();
    }
}
