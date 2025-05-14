package com.example.task_manager.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDto {
    @NotBlank
    @Size(max = 100)
    private String firstName;
    @NotBlank
    @Size(max = 100)
    private String lastName;
    @NotBlank
    @Email
    @Size(max = 255)
    private String email;
}
