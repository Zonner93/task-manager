package com.example.task_manager.controller;

import com.example.task_manager.constans.TaskStatus;
import com.example.task_manager.dto.request.TaskRequestDto;
import com.example.task_manager.dto.response.TaskResponseDto;
import com.example.task_manager.service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<Page<TaskResponseDto>> searchTasks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dueDateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dueDateTo,
            Pageable pageable) {

        return ResponseEntity.ok(taskService.getTasks(title, description, status, dueDateFrom, dueDateTo, pageable));
    }


    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody @Valid TaskRequestDto requestDto) {
        TaskResponseDto created = taskService.createTask(requestDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(
            @PathVariable Long id,
            @RequestBody @Valid TaskRequestDto requestDto) {
        return ResponseEntity.ok(taskService.updateTask(id, requestDto));
    }

    @PatchMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<TaskResponseDto> assignUserToTask(
            @PathVariable Long taskId,
            @PathVariable Long userId) {
        TaskResponseDto taskResponseDto = taskService.assignUserToTask(taskId, userId);
        return ResponseEntity.ok(taskResponseDto);
    }


    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponseDto> changeTaskStatus(
            @PathVariable Long id,
            @RequestParam TaskStatus status) {
        return ResponseEntity.ok(taskService.changeTaskStatus(id, status));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }


}
