package com.example.todoapp;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("testing")
class TodoAppApplicationTests {

	@Autowired
	MockMvc mvc;

	@Autowired
	TaskController controller;

	@Test
	@Order(1)
	void exceptionWhenRepositoryIsEmpty() throws Exception {
		mvc.perform(get("/todo/")).andExpect(status().isNotFound());
	}

	@Test
	@Order(2)
	void createAndDeleteNewTask() throws Exception {

		String contentBody = "{\"description\" : \"First Task\"}";

		mvc.perform(post("/todo/")
						.contentType("application/json")
						.content(contentBody))
				.andExpectAll(
						status().isOk(),
						content().contentType("application/json"));

		mvc.perform(delete("/todo/1")).andExpect(status().isOk());

	}

	@Test
	void exceptionWhenDeletingUnknownTask() throws Exception {
		mvc.perform(delete("/todo/42")).andExpect(status().isNotFound());
	}
	@Test
	void exceptionWhenGettingUnknownTask() throws Exception {
		mvc.perform(get("/todo/42")).andExpect(status().isNotFound());
	}
	@Test
	void completingTask() throws Exception {
		String contentBody = "{\"description\" : \"Second Task\"}";

		mvc.perform(post("/todo/")
				.contentType("application/json")
				.content(contentBody));
		mvc.perform(put("/todo/2/complete")).andExpectAll(
				status().isOk());
	}
}
