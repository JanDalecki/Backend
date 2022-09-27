package com.example.backend;

public class NoTasksAvailableException extends RuntimeException {
    public NoTasksAvailableException(){
        super("No tasks are present in the database.");
    }

}
