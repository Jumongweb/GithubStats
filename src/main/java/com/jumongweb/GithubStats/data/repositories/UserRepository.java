package com.jumongweb.GithubStats.data.repositories;

import com.jumongweb.GithubStats.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
