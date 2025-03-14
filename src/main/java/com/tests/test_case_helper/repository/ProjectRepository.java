package com.tests.test_case_helper.repository;

import com.tests.test_case_helper.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("FROM Project p WHERE p.id = :id")
    Optional<Project> findProjectById(Long id);
}
