package com.example.task_manager;

import com.example.task_manager.dto.request.UserRequestDto;
import com.example.task_manager.model.User;
import com.example.task_manager.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    void testCreateUser() throws Exception {
        UserRequestDto dto = new UserRequestDto("Alice", "Johnson", "alice@example.com");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Alice"));
    }

    @Test
    void testGetUserById() throws Exception {
        User user = new User("Bob", "Smith", "bob@example.com");
        user = userRepository.save(user);

        mockMvc.perform(get("/api/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("bob@example.com"));
    }

    @Test
    void testGetAllUsers() throws Exception {
        userRepository.saveAll(List.of(
                new User("Frank", "King", "frank@example.com"),
                new User("Grace", "Queen", "grace@example.com")
        ));

        mockMvc.perform(get("/api/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testDeleteUserById() throws Exception {
        User user = new User("Charlie", "Mills", "charlie@example.com");
        user = userRepository.save(user);

        mockMvc.perform(delete("/api/users/" + user.getId()))
                .andExpect(status().isNoContent());
    }


    @Test
    void testSearchUsers() throws Exception {
        userRepository.saveAll(List.of(
                new User("David", "Brown", "david.brown@example.com"),
                new User("Eva", "Novak", "eva.novak@example.com"),
                new User("Eva", "Smith", "eva.smith@example.com")
        ));

        mockMvc.perform(get("/api/users")
                        .param("page", "0")
                        .param("firstName", "Eva")
                        .param("lastName", "mith")

                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].firstName").value("Eva"));
    }


}
