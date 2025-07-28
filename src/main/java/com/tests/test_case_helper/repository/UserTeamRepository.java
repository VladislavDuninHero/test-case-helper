package com.tests.test_case_helper.repository;

import com.tests.test_case_helper.entity.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {

    @Query("""
        FROM UserTeam ut 
        JOIN FETCH ut.user            
        JOIN FETCH ut.team
        WHERE ut.team.id = :id AND ut.user.id = :userId
    """)
    Optional<UserTeam> getTeammateInTeamById(long id, long userId);

    @Modifying
    @Query("DELETE FROM UserTeam ut WHERE ut.team.id = :id AND ut.user.id = :userId")
    void deleteTeammateInTeamById(long id, long userId);

    @Query("FROM UserTeam ut WHERE ut.team.id = :id")
    List<UserTeam> getAllTeammatesInTeamById(long id);
}
