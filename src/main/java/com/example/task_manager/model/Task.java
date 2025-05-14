package com.example.task_manager.model;

import com.example.task_manager.constans.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String title;

    @Size(max = 5000)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @NotNull
    @FutureOrPresent
    private LocalDateTime dueDate;

    @ManyToMany
    private Set<User> assignedUsers = new HashSet<>();
}
