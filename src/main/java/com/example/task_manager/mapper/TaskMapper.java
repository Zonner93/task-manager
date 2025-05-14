package com.example.task_manager.mapper;

import com.example.task_manager.dto.response.TaskResponseDto;
import com.example.task_manager.model.Task;
import com.example.task_manager.model.User;

import java.util.HashSet;
import java.util.Set;

public class TaskMapper {
    public TaskResponseDto toResponseDto(Task task) {
        return TaskResponseDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus().toString())
                .dueDate(task.getDueDate())
                .assignedUsersId(extractIds(task.getAssignedUsers()))
                .build();
    }

    private Set<Long> extractIds(Set<User> assignedUsers) {
        Set<Long> userIds = new HashSet<>();

        for (User user : assignedUsers) {
            userIds.add(user.getId());
        }
        return userIds;
    }
}
