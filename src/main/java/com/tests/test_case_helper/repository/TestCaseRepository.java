package com.tests.test_case_helper.repository;

import com.tests.test_case_helper.entity.cases.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {

    @Query("FROM TestCase tc WHERE tc.testSuite.id = :testSuiteId")
    List<TestCase> getAllTestCasesByTestSuiteId(Long testSuiteId);
}
