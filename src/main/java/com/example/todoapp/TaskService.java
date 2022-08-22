package com.example.todoapp;

import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public Task saveTask(Task taskToSave){
        return repository.save(taskToSave);
    }

    public void deleteTask(Long id){
        repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        repository.deleteById(id);
    }

    public Task completeTask(Long id){
        Optional<Task> response = repository.findById(id);
        Task task = response.orElseThrow(() -> new TaskNotFoundException(id));
        task.setTaskCompletion(true);
        return repository.save(task);
    }

    public List<Task> getAllTasks(){
        List<Task> response = repository.findAll();
        if(response.isEmpty()){
            throw new NoTasksAvailableException();
        }
        return response;
    }

    public Task getTaskById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

}
