package com.example.task_manager.exception.user;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String id) {
        super("User not found with id: " + id);
    }
}
