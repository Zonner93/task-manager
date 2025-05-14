package com.example.task_manager.service;

import com.example.task_manager.dto.request.UserRequestDto;
import com.example.task_manager.dto.response.UserResponseDto;
import com.example.task_manager.exception.UserAlreadyExistsException;
import com.example.task_manager.exception.UserNotFoundException;
import com.example.task_manager.mapper.UserMapper;
import com.example.task_manager.model.User;
import com.example.task_manager.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Page<UserResponseDto> getUsers(String firstName, String lastName, String email, Pageable pageable) {
        if (firstName != null) {
            firstName = firstName.toLowerCase();
        }
        if (lastName != null) {
            lastName = lastName.toLowerCase();
        }
        if (email != null) {
            email = email.toLowerCase();
        }
        Page<User> users = userRepository.findByFilter(firstName, lastName, email, pageable);

        return users.map(UserMapper::toResponseDto);
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::toResponseDto).toList();
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new UserAlreadyExistsException(userRequestDto.getEmail());
        }
        User user = UserMapper.toEntity(userRequestDto);
        User savedUser = userRepository.save(user);
        return UserMapper.toResponseDto(savedUser);
    }

    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id.toString());
        }
        userRepository.deleteById(id);
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString()));
        return UserMapper.toResponseDto(user);
    }
}
