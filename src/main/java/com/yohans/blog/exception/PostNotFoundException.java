package com.yohans.blog.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)  // This will return 404 status
public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(String message) {
        super(message);
    }
}


