package com.tests.test_case_helper.repository;

import com.tests.test_case_helper.entity.Project;
import com.tests.test_case_helper.repository.projections.ProjectTeamProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("FROM Project p WHERE p.id = :id")
    Optional<Project> findProjectById(Long id);

    @Query("""
            SELECT 
                    p.id as id, 
                    p.title as title, 
                    p.team.teamName as teamName, 
                    p.description as description 
            FROM Project p 
            WHERE p.team.id 
            IN :teamIds
            """)
    List<ProjectTeamProjection> findAllByTeamIds(Collection<Long> teamIds);
}
