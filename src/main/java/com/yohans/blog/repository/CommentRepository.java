package com.yohans.blog.repository;

import com.yohans.blog.model.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Find comments by post ID to display all comments on a specific post
    List<Comment> findByPostId(Long postId);

    // Optional: Find comments by a specific user
    List<Comment> findByUserUsername(String username);
}


