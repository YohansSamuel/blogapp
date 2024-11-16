package com.yohans.blog.service;

import com.yohans.blog.exception.CommentNotFoundException;
import com.yohans.blog.exception.PostNotFoundException;
import com.yohans.blog.exception.UnauthorizedActionException;
import com.yohans.blog.model.Comment;
import com.yohans.blog.model.Post;
import com.yohans.blog.model.BlogUser;
import com.yohans.blog.repository.CommentRepository;
import com.yohans.blog.repository.PostRepository;
import com.yohans.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // Get all comments for a specific post
    public List<Comment> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));

        return post.getComments();
    }

    // Create a comment for a post
    public Comment createComment(Long postId, Comment comment, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));

        BlogUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedActionException("User not found: " + username));

        comment.setPost(post);
        comment.setUser(user);

        return commentRepository.save(comment);
    }

    // Update a comment
    public Comment updateComment(Long commentId, Comment comment, String username) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with id: " + commentId));

        if (!existingComment.getUser().getUsername().equals(username)) {
            throw new UnauthorizedActionException("You can only update your own comments");
        }

        existingComment.setContent(comment.getContent());
        return commentRepository.save(existingComment);
    }

    // Delete a comment
    public void deleteComment(Long commentId, String username) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with id: " + commentId));

        if (!existingComment.getUser().getUsername().equals(username)) {
            throw new UnauthorizedActionException("You can only delete your own comments");
        }

        commentRepository.deleteById(commentId);
    }
}
