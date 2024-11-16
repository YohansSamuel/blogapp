package com.yohans.blog.service;

import com.yohans.blog.exception.PostNotFoundException;
import com.yohans.blog.exception.UnauthorizedActionException;
import com.yohans.blog.exception.UserNotFoundException;
import com.yohans.blog.model.Post;
import com.yohans.blog.model.BlogUser;
import com.yohans.blog.repository.PostRepository;
import com.yohans.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // Get all posts
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // Get a post by its ID
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    // Create a new post
    public Post createPost(Post post, String username) {
        BlogUser user = userRepository.findByUsername(username)
//        BlogUser user = userRepository.findByUsername("admin")
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));
        post.setUser(user);  // Associate the post with the user
        return postRepository.save(post);
    }

    // Update an existing post
    public Post updatePost(Long id, Post post, String username) {
        BlogUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new PostNotFoundException("User not found: " + username));  // Note: this should be UserNotFoundException

        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));

        // Check if the user is the author of the post
        if (!existingPost.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedActionException("You can only update your own posts");
        }

        post.setUser(user);  // Ensure post is associated with the correct user
        post.setId(id);  // Set the ID to ensure it's the same post being updated
        return postRepository.save(post);
    }

    // Delete a post
    public void deletePost(Long id, String username) {
        BlogUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new PostNotFoundException("User not found: " + username));  // Note: this should be UserNotFoundException

        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));

        // Check if the user is the author of the post
        if (!existingPost.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedActionException("You can only delete your own posts");
        }

        postRepository.deleteById(id);
    }
}
