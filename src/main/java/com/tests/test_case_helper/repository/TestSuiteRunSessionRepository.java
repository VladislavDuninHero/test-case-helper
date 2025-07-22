package com.tests.test_case_helper.repository;

import com.tests.test_case_helper.entity.TestSuite;
import com.tests.test_case_helper.entity.TestSuiteRunSession;
import com.tests.test_case_helper.entity.User;
import com.tests.test_case_helper.enums.TestSuiteRunStatus;
import com.tests.test_case_helper.repository.projections.TestSuiteRunSessionProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TestSuiteRunSessionRepository extends JpaRepository<TestSuiteRunSession, Long> {

    @Query("""
            FROM TestSuiteRunSession tsrs 
            JOIN FETCH tsrs.testCaseRunResults
            JOIN FETCH tsrs.testSuite
            JOIN FETCH tsrs.executedBy
            WHERE tsrs.id = :runSessionId
    """)
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
    JOIN tsrs.testSuite ts
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

    @Query("""
    SELECT
        tsrs.id as id,
        ts as testSuite,
        tsrs.startTime as startTime,
        tsrs.endTime as endTime,
        tsrs.environment as environment,
        tsrs.status as status
    FROM TestSuiteRunSession tsrs
    JOIN tsrs.testSuite ts
    WHERE tsrs.status = 'IN_PROGRESS'
    """)
    List<TestSuiteRunSessionSlimProjection> getActiveTestSuiteRunSessions();

    @Query("""
    SELECT
        tsrs.id as id,
        user as executedBy,
        tsrs.testSuite.title as testSuiteTitle,
        tsrs.environment as environment,
        tsrs.status as status,
        tsrs.startTime as startTime,
        tsrs.endTime as endTime
    FROM TestSuiteRunSession tsrs
    JOIN tsrs.executedBy user
    JOIN tsrs.testSuite suite
    WHERE tsrs.id = :runSessionId
    """)
    Optional<TestSuiteRunSessionProjection> getTestSuiteRunSessionSlimById(Long runSessionId);

    @Modifying
    @Query("UPDATE TestSuiteRunSession tsrs SET tsrs.status = :status, tsrs.endTime = :endTime WHERE tsrs.id = :runSessionId")
    void endTestSuiteRunSessionById(Long runSessionId, LocalDateTime endTime, TestSuiteRunStatus status);

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
