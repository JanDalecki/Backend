package com.example.backend;

import java.util.Random;

public class TaskBuilder {
    String description;
    Long id;

    boolean completed;
    static Random random = new Random();

    private TaskBuilder(){
        description = "Sample Task";
        id = 1L;
        completed = false;
    };

    public TaskBuilder with(String description){
        this.description = description;
        return this;
    }

    public TaskBuilder with(Long id){
        this.id = id;
        return this;
    }

    public TaskBuilder with(boolean completion){
        this.completed = completion;
        return this;
    }

    public TaskBuilder withRandomId(){
        id = Math.abs(random.nextLong());
        return this;
    }

    public TaskBuilder withRandomDescription(){

        int length = random.nextInt(60 - 5) + 5;
        char[] buff = new char[length];

        for (int i = 0; i < length; ++i)
            buff[i] = (char) (random.nextInt(126 - 33) + 33);

        description = new String(buff);
        return this;
    }

    public Task build(){
        return new Task(description, id, completed);
    }

    public static TaskBuilder generateTask(){
        return new TaskBuilder();
    }

}
