package com.example.todoapp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Task {

    private String description;
    private boolean taskCompleted;
    private @GeneratedValue @Id Long id;

    public Task(String description, Long id){
        this.description = description;
        this.id = id;
        taskCompleted = false;
    }

    public Task(String description) {
        this.description = description;
        taskCompleted = false;
    }

    public Task() {
        description = "Write a description for your task";
        taskCompleted = false;
    }

    public boolean isCompleted(){
        return taskCompleted;
    }

    public void setTaskCompletion(boolean completion){
        this.taskCompleted = completion;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o instanceof Task){
            return ((Task) o).getId() == id && ((Task) o).getDescription() == description;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.description);
    }

    @Override
    public String toString() {
        return "Task : " + id + "\n" + "Description : " + description;
    }
}
