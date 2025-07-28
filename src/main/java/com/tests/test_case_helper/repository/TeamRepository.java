package com.tests.test_case_helper.repository;

import com.tests.test_case_helper.entity.Team;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "teammates",
                    "teammates.user"
            }
    )
    @Query("FROM Team t WHERE t.id = :id")
    Optional<Team> findTeamById(Long id);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "teammates",
                    "teammates.user"
            }
    )
    @Query("FROM Team t WHERE t.teamName = :name")
    Optional<Team> findTeamByName(String name);
}
