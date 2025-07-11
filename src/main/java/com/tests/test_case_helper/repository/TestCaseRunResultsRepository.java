package com.tests.test_case_helper.repository;

import com.tests.test_case_helper.entity.TestCaseRunResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TestCaseRunResultsRepository extends JpaRepository<TestCaseRunResult, Long> {
    @Query("""
            FROM TestCaseRunResult tcrr 
            WHERE tcrr.testSuiteRunSession.id = :runSessionId 
            AND tcrr.testSuiteRunSession.testSuite.id = :testSuiteId
    """)
    List<TestCaseRunResult> findAllTestCaseResultsByRunSessionId(Long testSuiteId, Long runSessionId, Pageable pageable);
}
