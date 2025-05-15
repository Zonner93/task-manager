package com.example.task_manager.mapper;

import com.example.task_manager.dto.request.UserRequestDto;
import com.example.task_manager.dto.response.UserResponseDto;
import com.example.task_manager.model.User;

public class UserMapper {

    public static UserResponseDto toResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    public static User toEntity(UserRequestDto userRequestDto) {
        return User.builder()
                .firstName(userRequestDto.getFirstName())
                .lastName(userRequestDto.getLastName())
                .email(userRequestDto.getEmail())
                .build();
    }
}
