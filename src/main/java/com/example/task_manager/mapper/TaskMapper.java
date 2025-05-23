package com.example.task_manager.mapper;

import com.example.task_manager.constans.TaskStatus;
import com.example.task_manager.dto.request.TaskRequestDto;
import com.example.task_manager.dto.response.TaskResponseDto;
import com.example.task_manager.model.Task;
import com.example.task_manager.model.User;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TaskMapper {
    public static Task toEntity(TaskRequestDto requestDto) {
        return Task.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .dueDate(requestDto.getDueDate())
                .status(TaskStatus.UNASSIGNED)
                .build();
    }

    public static TaskResponseDto toResponseDto(Task task) {
        return TaskResponseDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus().toString())
                .dueDate(task.getDueDate())
                .assignedUsersId(extractIds(task.getAssignedUsers()))
                .build();
    }

    private static Set<Long> extractIds(Set<User> assignedUsers) {
        if (Objects.isNull(assignedUsers)) {
            return new HashSet<>();
        }
        Set<Long> userIds = new HashSet<>();

        for (User user : assignedUsers) {
            userIds.add(user.getId());
        }
        return userIds;
    }
}
