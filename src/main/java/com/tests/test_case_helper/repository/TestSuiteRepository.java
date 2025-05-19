package com.tests.test_case_helper.repository;

import com.tests.test_case_helper.entity.TestSuite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TestSuiteRepository extends JpaRepository<TestSuite, Long> {

    @Query("FROM TestSuite t WHERE t.id = :id")
    Optional<TestSuite> getTestSuiteById(Long id);

    @Query("FROM TestSuite t WHERE t.id = :id")
    Page<TestSuite> getTestSuiteById(Long id, Pageable pageable);
}
