package com.yohans.blog.service;

import com.yohans.blog.model.BlogUser;
import com.yohans.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // Retrieve the user from the database using the username
//        BlogUser blogUser = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//
//        // Return UserDetails with the correct roles (empty list for demo purposes)
//        return new User(blogUser.getUsername(), blogUser.getPassword(), new ArrayList<>());
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Hardcoded admin user for the first login (for testing or initial setup)
        if ("admin".equals(username)) {
            return new User("admin", new BCryptPasswordEncoder().encode("admin"), new ArrayList<>());
//            return new User("admin", "admin", new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
