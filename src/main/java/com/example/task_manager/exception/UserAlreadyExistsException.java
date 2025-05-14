package com.example.task_manager.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException (String email) {
        super("User already exists with email: " + email);
    }
}
