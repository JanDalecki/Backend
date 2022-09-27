package com.example.backend;

import org.springframework.context.annotation.Configuration;
import java.util.List;

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
        Task taskToDelete = repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        repository.delete(taskToDelete);
    }
    public Task completeTask(Long id, boolean completion){
        Task task = repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        task.setTaskCompletion(completion);
        return task;
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
