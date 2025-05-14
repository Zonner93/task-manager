package com.example.task_manager.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private String status;
    private LocalDateTime dueDate;
    private Set<Long> assignedUsersId;
}
