package com.example.task_manager.mapper;

import com.example.task_manager.dto.UserDto;
import com.example.task_manager.model.User;

public class UserMapper {

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}
