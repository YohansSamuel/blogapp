package com.yohans.blog.controller;

import com.yohans.blog.model.Comment;
import com.yohans.blog.model.Post;
import com.yohans.blog.service.CommentService;
import com.yohans.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    // Get all comments for a specific post
    @GetMapping
    public List<Comment> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    // Create a new comment for a post
    @PostMapping
    public ResponseEntity<Comment> createComment(@PathVariable Long postId, @RequestBody Comment comment, Principal principal) {
        Comment createdComment = commentService.createComment(postId, comment, principal.getName());
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    // Update an existing comment
    @PutMapping("/{commentId}")
    public Comment updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody Comment comment, Principal principal) {
        return commentService.updateComment(commentId, comment, principal.getName());
    }

    // Delete a comment
    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long postId, @PathVariable Long commentId, Principal principal) {
        commentService.deleteComment(commentId, principal.getName());
    }
}
