package com.yohans.blog.service;

import com.yohans.blog.exception.UserNotFoundException;
import com.yohans.blog.model.BlogUser;
import com.yohans.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Get a user by username
    public Optional<BlogUser> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Get a user by ID
    public Optional<BlogUser> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Create a new user
    public BlogUser createUser(BlogUser user) {
        return userRepository.save(user);
    }

    // Update an existing user
    public BlogUser updateUser(Long id, BlogUser user) {
        BlogUser existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        existingUser.setUsername(user.getUsername());
//        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());

        return userRepository.save(existingUser);
    }

    // Delete a user
    public void deleteUser(Long id) {
        BlogUser user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        userRepository.delete(user);
    }


    // Method to retrieve all users
    public List<BlogUser> getAllUsers() {
        return userRepository.findAll(); // Assuming 'findAll' is defined in the repository
    }


}
