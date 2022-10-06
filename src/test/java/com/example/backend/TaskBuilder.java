package com.example.backend;

import java.util.Random;

public class TaskBuilder {

    Task task;
    String description;
    Long id;
    static Random random = new Random();

    boolean completed;

    private TaskBuilder(){
        description = "Sample Task";
        id = 1L;
        task = new Task(description, id);
    };

    public TaskBuilder with(String description){
        task.changeDescription(description);
        return this;
    }

    public TaskBuilder with(Long id){
        task.setId(id);
        return this;
    }

    public TaskBuilder with(boolean completed){
        task.setTaskCompletion(completed);
        return this;
    }

    public TaskBuilder withRandomId(){
        task.setId(Math.abs(random.nextLong()));
        return this;
    }

    public TaskBuilder withRandomDescription(){

        int length = random.nextInt(60 - 5) + 5;
        char[] buff = new char[length];

        for (int i = 0; i < length; ++i)
            buff[i] = (char) (random.nextInt(126 - 33) + 33);

        task.changeDescription(new String(buff));
        return this;
    }

    public Task build(){
        return task;
    }

    public static TaskBuilder generateTask(){
        return new TaskBuilder();
    }

}
