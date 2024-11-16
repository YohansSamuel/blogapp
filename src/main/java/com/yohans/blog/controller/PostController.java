package com.yohans.blog.controller;

import com.yohans.blog.exception.PostNotFoundException;
import com.yohans.blog.exception.UnauthorizedActionException;
import com.yohans.blog.exception.UserNotFoundException;
import com.yohans.blog.model.Post;
import com.yohans.blog.service.PostService;
import com.yohans.blog.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

//    @Autowired
//    public PostController(PostService postService, UserService userService) {
//        this.postService = postService;
//        this.userService = userService;
//    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id) {
        return postService.getPostById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post, Principal principal) {
        String username = principal.getName();
        Post createdPost = postService.createPost(post, username);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post post, Principal principal) {
        return postService.updatePost(id, post, principal.getName());
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id, Principal principal) {
        postService.deletePost(id, principal.getName());
    }

    @GetMapping("/hello")
    public String hello() {
        return "Selam, welcome to my blog!";
    }
}
