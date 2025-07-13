package com.tests.test_case_helper.repository;

import com.tests.test_case_helper.entity.TestSuite;
import com.tests.test_case_helper.entity.TestSuiteRunSession;
import com.tests.test_case_helper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TestSuiteRunSessionRepository extends JpaRepository<TestSuiteRunSession, Long> {

    @Query("FROM TestSuiteRunSession tsrs WHERE tsrs.id = :runSessionId")
    Optional<TestSuiteRunSession> getTestSuiteRunSessionById(Long runSessionId);

    @Query("""
    SELECT
        tsrs.id as id,
        tsrs.testSuite as testSuite,
        tsrs.startTime as startTime,
        tsrs.endTime as endTime,
        tsrs.executedBy as executedBy,
        tsrs.environment as environment,
        tsrs.status as status
    FROM TestSuiteRunSession tsrs
    WHERE tsrs.id = :runSessionId AND tsrs.testSuite.id = :testSuiteId
    """)
    Optional<TestSuiteRunSessionSlimProjection> getTestSuiteRunSessionSlimById(Long testSuiteId, Long runSessionId);

    @Query("""
    SELECT
        tsrs.id as id,
        tsrs.testSuite as testSuite,
        tsrs.startTime as startTime,
        tsrs.endTime as endTime,
        tsrs.executedBy as executedBy,
        tsrs.environment as environment,
        tsrs.status as status
    FROM TestSuiteRunSession tsrs
    WHERE tsrs.executedBy.id = :userId AND tsrs.status = 'IN_PROGRESS'
    """)
    List<TestSuiteRunSessionSlimProjection> getTestSuiteRunSessionsSlimByUserId(Long userId);

    interface TestSuiteRunSessionSlimProjection {
        Long getId();
        TestSuite getTestSuite();
        LocalDateTime getStartTime();
        LocalDateTime getEndTime();
        User getExecutedBy();
        String getEnvironment();
        String getStatus();
    }
}
