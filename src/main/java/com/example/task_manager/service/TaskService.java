package com.example.task_manager.service;

import com.example.task_manager.constans.TaskStatus;
import com.example.task_manager.dto.request.TaskRequestDto;
import com.example.task_manager.dto.response.TaskResponseDto;
import com.example.task_manager.exception.task.TaskNotFoundException;
import com.example.task_manager.exception.user.UserNotFoundException;
import com.example.task_manager.mapper.TaskMapper;
import com.example.task_manager.model.Task;
import com.example.task_manager.model.User;
import com.example.task_manager.repository.TaskRepository;
import com.example.task_manager.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskResponseDto createTask(TaskRequestDto requestDto) {
        Task task = TaskMapper.toEntity(requestDto);
        task.setStatus(TaskStatus.UNASSIGNED);
        Task savedTask = taskRepository.save(task);
        return TaskMapper.toResponseDto(savedTask);

    }

    public TaskResponseDto getTaskById(Long id) {
        Task task = taskRepository.findTaskByIdWithUsers(id)
                .orElseThrow(() -> new TaskNotFoundException(id.toString()));
        return TaskMapper.toResponseDto(task);
    }

    public void deleteTaskById(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id.toString());
        }
        taskRepository.deleteById(id);
    }

    public TaskResponseDto updateTask(Long id, TaskRequestDto requestDto) {
        return null;
    }

    public Page<TaskResponseDto> getTasks(String title, String description, TaskStatus status, LocalDateTime dueDateFrom, LocalDateTime dueDateTo, Pageable pageable) {
        if (title != null) {
            title = title.toLowerCase();
        }
        if (description != null) {
            description = description.toLowerCase();
        }

        Page<Task> tasks = taskRepository.findByFilter(
                title, description, status, dueDateFrom, dueDateTo, pageable);

        return tasks.map(TaskMapper::toResponseDto);
    }

    public TaskResponseDto assignUserToTask(Long taskId, Long userId) {
        Task task = taskRepository.findTaskByIdWithUsers(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId.toString()));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));

        task.getAssignedUsers().add(user);
        Task updatedTask = taskRepository.save(task);
        taskRepository.save(updatedTask);
        return TaskMapper.toResponseDto(updatedTask);
    }

    public TaskResponseDto changeTaskStatus(Long taskId, TaskStatus status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId.toString()));

        task.setStatus(status);
        Task updated = taskRepository.save(task);
        return TaskMapper.toResponseDto(updated);
    }
}
