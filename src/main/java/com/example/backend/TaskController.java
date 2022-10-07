package com.example.backend;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5173")
@RestController
@RequestMapping("/todo")
@Log4j2
public class TaskController {
    private final TaskService service;
    @Autowired
    public TaskController(TaskService service){
        log.info("Controller created");
        this.service = service;
    }
    @PostMapping("")
    public Task createTask(@RequestBody Task task){
        log.info("Create task");
        return service.saveTask(task);
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        log.info("GET task");
        return service.getTaskById(id);
    }

    @GetMapping("")
    public List<Task> getAllTasks() {
        log.info("GET all tasks");
        return service.getAllTasks();
    }

    @PutMapping("/{id}/completion/true")
    public Task completeTask(@PathVariable Long id) {
        log.info("Completed task");
        return service.completeTask(id, true);
    }
    @PutMapping("/{id}/completion/false")
    public Task uncompleteTask(@PathVariable Long id) {
        log.info("Uncompleted task");
        return service.completeTask(id, false);
    }
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id){
        log.info("Deleted task");
        service.deleteTask(id);
    }
}
