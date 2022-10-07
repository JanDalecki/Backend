package com.example.backend;

import lombok.*;

import javax.persistence.*;


@Data
@Entity
@Table(name = "tasks")
public class Task {
    private final String description;
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    Long id;

    private boolean completed;

    public Task(String description){
        this.description = description;
        this.completed = false;
    }

    public Task(String description, Long id, boolean completed){
        this.description = description;
        this.completed = completed;
        this.id = id;
    }
    public Task() {
        this.description = "Sample Task";
        this.completed = false;
    }
}
