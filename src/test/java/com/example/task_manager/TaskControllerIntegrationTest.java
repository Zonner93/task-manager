package com.example.task_manager;

import com.example.task_manager.constans.TaskStatus;
import com.example.task_manager.dto.request.TaskRequestDto;
import com.example.task_manager.model.Task;
import com.example.task_manager.model.User;
import com.example.task_manager.repository.TaskRepository;
import com.example.task_manager.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private Long taskId;
    private Long userId;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user = userRepository.save(user);
        userId = user.getId();

        TaskRequestDto taskRequestDto = new TaskRequestDto("Test Task", "Test Description", LocalDateTime.now().plusDays(1));

        try {
            String content = objectMapper.writeValueAsString(taskRequestDto);
            String response = mockMvc.perform(post("/api/tasks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isCreated())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            taskId = objectMapper.readTree(response).get("id").asLong();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldGetTaskById() throws Exception {
        mockMvc.perform(get("/api/tasks/" + taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    void shouldChangeTaskStatus() throws Exception {
        mockMvc.perform(patch("/api/tasks/" + taskId + "/status")
                        .param("status", "DONE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DONE"));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        mockMvc.perform(delete("/api/tasks/" + taskId))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldUpdateTask() throws Exception {
        TaskRequestDto updated = new TaskRequestDto("Updated Task", "Updated Description", LocalDateTime.now().plusDays(2));
        mockMvc.perform(put("/api/tasks/" + taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task"));
    }

    @Test
    void shouldAssignUserToTask() throws Exception {
        mockMvc.perform(patch("/api/tasks/" + taskId + "/assign/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assignedUsersId[0]").value(userId));
    }

    @Test
    void shouldReturnFilteredTasks() throws Exception {
        Task task1 = new Task("Task A", "Desc A", TaskStatus.UNASSIGNED, LocalDateTime.now().plusDays(1));
        Task task2 = new Task("Task B", "Desc B", TaskStatus.DONE, LocalDateTime.now().plusDays(2));
        Task task3 = new Task("Important Task", "Urgent", TaskStatus.DONE, LocalDateTime.now().plusDays(3));


        taskRepository.saveAll(List.of(task1, task2, task3));


        mockMvc.perform(get("/api/tasks")
                        .param("title", "Task")
                        .param("status", "DONE")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[1].status").value("DONE"));
    }
}
