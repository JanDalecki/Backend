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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTests {

    @Mock TaskRepository repository;
    TaskService service;

    @BeforeEach
    void setUp() {
        service = new TaskService(repository);
    }

    @Test
    void gettingTasksById() {
        final var expectedTask = new Task("Example task", 1L);
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
        Task firstTask = new Task("First task", 1L);
        Task secondTask = new Task("Second task", 2L);
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
        Task firstTask = new Task("First task", 1L);
        when(repository.findById(1L)).thenReturn(Optional.of(firstTask));
        service.completeTask(1L, true);
        Assertions.assertTrue(firstTask.isCompleted());
    }
    @Test
    @Description("Should complete task")
    void uncompletingTasks() {
        Task firstTask = new Task("First task", 1L);
        when(repository.findById(1L)).thenReturn(Optional.of(firstTask));
        service.completeTask(1L, false);
        Assertions.assertFalse(firstTask.isCompleted());
    }
    @Test
    void saveTaskTest() {
        Task newTask = new Task("Example task");
        service.saveTask(newTask);
        verify(repository).save(newTask);
    }

    @Test
    void deleteTaskTest() {
        Task newTask = new Task("Example task");
        when(repository.findById(newTask.getId())).thenReturn(Optional.of(newTask));
        service.deleteTask(newTask.getId());
        verify(repository).delete(newTask);
    }

    @Test
    void deleteTaskException() {
        Assertions.assertThrows(TaskNotFoundException.class, () -> service.deleteTask(1L));
    }
}