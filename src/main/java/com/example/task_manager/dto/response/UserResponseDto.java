package com.example.task_manager.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
