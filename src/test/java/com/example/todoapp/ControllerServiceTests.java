package com.example.todoapp;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TaskController.class)
@ActiveProfiles("testing")
public class ControllerServiceTests {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    TaskService service;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Tests the POST method for creating a new task")
    void createNewTask() throws Exception {

        String contentBody = "{\"description\" : \"First Task\"}";

        mockMvc.perform(post("/todo/")
                .contentType("application/json")
                .content(contentBody))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("Not Found status when repository is empty")
    void noTasksExceptionWhenRepositoryEmpty() throws Exception {
        given(service.getAllTasks()).willThrow(new NoTasksAvailableException());
        mockMvc.perform(get("/todo/"))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }
}