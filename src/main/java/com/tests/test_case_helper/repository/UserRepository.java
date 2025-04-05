package com.tests.test_case_helper.repository;

import com.tests.test_case_helper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("FROM User u WHERE u.login = :login")
    Optional<User> findUserByLogin(String login);

    @Query("FROM User u WHERE u.id = :userId")
    Optional<User> findUserById(Long userId);
}
