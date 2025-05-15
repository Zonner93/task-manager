package com.example.task_manager.exception.task;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException (String id) {
        super("Task not found with id: " + id);
    }
}
