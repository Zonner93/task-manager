package com.example.task_manager.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TaskRequestDto {
    @NotNull
    @Size(max = 255)
    private String title;

    @Size(max = 5000)
    @NotNull
    private String description;

    @NotNull
    @FutureOrPresent
    private LocalDateTime dueDate;
}
