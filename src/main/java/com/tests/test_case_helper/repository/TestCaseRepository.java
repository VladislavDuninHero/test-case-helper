package com.tests.test_case_helper.repository;

import com.tests.test_case_helper.entity.cases.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
}
