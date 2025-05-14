package com.example.task_manager.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRequestDto {
    private String title;
    private String description;
    private String status;
    private LocalDateTime dueDate;
}
