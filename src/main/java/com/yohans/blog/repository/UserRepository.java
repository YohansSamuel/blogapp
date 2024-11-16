package com.yohans.blog.repository;

import com.yohans.blog.model.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<BlogUser, Long> {
    // Find user by username for authentication and authorization
    Optional<BlogUser> findByUsername(String username);

    // Optional: Check if a username is already taken during registration
    boolean existsByUsername(String username);

}
