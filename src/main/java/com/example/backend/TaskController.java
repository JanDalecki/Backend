package com.example.backend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5173")
@RestController
@RequestMapping("/todo")
public class TaskController {
    private final TaskService service;
    private final Logger log = LogManager.getLogger();
    @Autowired
    public TaskController(TaskService service){
        log.info("Controller created");
        this.service = service;
    }
    @PostMapping("")
    public Task createTask(@RequestBody Task newTask){
        log.info("Create task");
        return service.saveTask(newTask);
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
