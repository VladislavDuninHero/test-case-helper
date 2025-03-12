package com.tests.test_case_helper.repository;

import com.tests.test_case_helper.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
