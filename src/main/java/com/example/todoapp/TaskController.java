package com.example.todoapp;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {

    private final TaskRepository repository;

    public TaskController(TaskRepository repository){
        this.repository = repository;
    }

    @PostMapping("/TODO")
    public Task createTask(@RequestBody Task newTask){
        return repository.save(newTask);
    }

    @GetMapping("/TODO/{id}")
    public Task getTaskById(@PathVariable Long id){
        return repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @GetMapping("/TODO")
    public List<Task> getAllTasks(){
        List<Task> response = repository.findAll();
        if(response.size() == 0){
            throw new NoTasksAvailableException();
        }
        return response;
    }

    @PutMapping("/TODO/{id}/complete")
    public Task completeTask(@PathVariable Long id){
        Optional<Task> response = repository.findById(id);
        Task task = response.orElseThrow(() -> new TaskNotFoundException(id));
        task.setTaskCompletion(true);
        return repository.save(task);
    }

    @DeleteMapping("/TODO/{id}")
    public void deleteTask(@PathVariable Long id){
        repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        repository.deleteById(id);
    }
}
