package com.example.backend;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("testing")
class TodoAppApplicationTests {

	@Autowired
	MockMvc mvc;
	@Autowired
	TaskRepository repository;

	@Autowired
	TaskController controller;

	@AfterEach
	void after(){
		repository.deleteAll();
	}

	@Test
	void exceptionWhenRepositoryIsEmpty() throws Exception {
		mvc.perform(get("/todo/")).andExpect(status().isNotFound());
	}
	@Test
	void createAndDeleteNewTask() throws Exception {

		String contentBody = "{\"description\" : \"First Task\"}";

		mvc.perform(post("/todo/")
						.contentType("application/json")
						.content(contentBody))
				.andExpectAll(
						status().isOk(),
						content().contentType("application/json"));
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
		String contentBody = "{\"description\" : \"Some Task\"}";

		mvc.perform(post("/todo/")
				.contentType("application/json")
				.content(contentBody));
		MvcResult result = mvc.perform(put("/todo/3/completion/true")).andExpect(status().isOk())
				.andExpect(jsonPath("$.completed", Matchers.is(true)))
				.andReturn();
		String content = result.getResponse().getContentAsString();
		assertThat(content).matches(".*\"completed\":true.*");
	}

	@Test
	void deletingTask() throws Exception {
		String contentBody = "{\"description\" : \"Sample Task\"}";

		mvc.perform(post("/todo/")
				.contentType("application/json")
				.content(contentBody));
		mvc.perform(get("/todo/2")).andExpect(status().isOk());

		mvc.perform(delete("/todo/2"))
				.andExpect(status().isOk());
		mvc.perform(get("/todo/"))
				.andExpect(status().isNotFound());
	}
}
