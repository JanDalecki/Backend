package com.example.todoapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class TaskController {
    private final TaskService service;
    @Autowired
    public TaskController(TaskService service){
        this.service = service;
    }
    @PostMapping("")
    public Task createTask(@RequestBody Task newTask){
        return service.saveTask(newTask);
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return service.getTaskById(id);
    }

    @GetMapping("")
    public List<Task> getAllTasks() {

        return service.getAllTasks();
    }

    @PutMapping("/{id}/complete")
    public Task completeTask(@PathVariable Long id) {

        return service.completeTask(id);
    }
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id){
        service.deleteTask(id);
    }
}
