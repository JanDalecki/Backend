package com.example.backend;


import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.example.backend.TaskBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTests {

    @Mock TaskRepository repository;
    TaskService service;

    @BeforeEach
    void setUp() {
        service = new TaskService(repository);
    }

    @Test
    void gettingTasksById() {
        //final var expectedTask = Task.builder().description("Sample task").id(1L).completed(false).build();
        var expectedTask = new Task("sample description");
        when(repository.findById(1L)).thenReturn(Optional.of(expectedTask));
        final var returnedTask = service.getTaskById(1L);
        Assertions.assertEquals(returnedTask, expectedTask);
    }

    @Test
    @Description("Should throw TaskNotFoundException")
    void gettingNonexistentTaskById() {
        Assertions.assertThrows(TaskNotFoundException.class, () -> service.getTaskById(2L));
    }
    @Test
    @Description("Should return all tasks")
    void gettingAllTask() {
        Task firstTask = generateTask().with("Some Task").withRandomId().build();
        Task secondTask = generateTask().withRandomDescription().with(2L).build();
        when(repository.findAll()).thenReturn(List.of(firstTask, secondTask));
        List<Task> tasks = service.getAllTasks();
        assertThat(tasks).hasSize(2).containsExactly(firstTask, secondTask);
    }
    @Test
    @Description("Should throw NoTasksAvailableException when repository is empty")
    void gettingAllTaskException() {
        Assertions.assertThrows(NoTasksAvailableException.class, () -> service.getAllTasks());
    }
    @Test
    @Description("Should complete task")
    void completingTasks() {
        Task firstTask = generateTask().withRandomDescription().with(1L).build();
        when(repository.findById(1L)).thenReturn(Optional.of(firstTask));
        service.completeTask(1L, true);
        Assertions.assertTrue(firstTask.isCompleted());
    }

    @Test
    @Description("Should uncomplete task")
    void uncompletingTasks() {
        Task firstTask = generateTask().withRandomDescription().with(1L).build();
        when(repository.findById(1L)).thenReturn(Optional.of(firstTask));
        service.completeTask(1L, false);
        Assertions.assertFalse(firstTask.isCompleted());
    }
    @Test
    void saveTaskTest() {
        Task newTask = generateTask().withRandomDescription().withRandomId().build();
        service.saveTask(newTask);
        verify(repository).save(newTask);
    }

    @Test
    void deleteTaskTest() {
        Task newTask = generateTask().withRandomDescription().withRandomId().build();
        when(repository.findById(newTask.getId())).thenReturn(Optional.of(newTask));
        service.deleteTask(newTask.getId());
        verify(repository).delete(newTask);
    }

    @Test
    void deleteTaskException() {
        Assertions.assertThrows(TaskNotFoundException.class, () -> service.deleteTask(1L));
    }
}