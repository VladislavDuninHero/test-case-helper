package com.tests.test_case_helper.repository;

import com.tests.test_case_helper.entity.TestCaseRunResult;
import com.tests.test_case_helper.enums.TestCaseStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TestCaseRunResultsRepository extends JpaRepository<TestCaseRunResult, Long> {
    @Query("""
            FROM TestCaseRunResult tcrr 
            WHERE tcrr.testSuiteRunSession.id = :runSessionId 
            AND tcrr.testSuiteRunSession.testSuite.id = :testSuiteId
    """)
    List<TestCaseRunResult> findAllTestCaseResultsByRunSessionId(Long testSuiteId, Long runSessionId, Pageable pageable);

    @Query("""
        FROM TestCaseRunResult tcrr WHERE tcrr.id = :id
    """)
    Optional<TestCaseRunResult> findFirstTestCaseResultById(Long id);

    @Query("""
        SELECT tcrr.status as status, 
               COUNT(tcrr.status) as count
        FROM TestCaseRunResult tcrr
        WHERE tcrr.testSuiteRunSession.id = :id
        GROUP BY tcrr.status
    """)
    List<StatusStatisticProjection> countAllStatusesForSession(Long id);
    interface StatusStatisticProjection {
        TestCaseStatus getStatus();
        Long getCount();
    }
}
