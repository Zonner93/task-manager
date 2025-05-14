package com.example.task_manager.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Data
public class TaskResponseDto {
    private Long id;
    private String title;
    private String description;
    private String status;
    private LocalDateTime dueDate;
    private Set<Long> assignedUsersId;
}
