package com.example.backend;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NoTaskAvailableExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(NoTasksAvailableException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String noTaskAvailableExceptionHandler(NoTasksAvailableException ex){
        return ex.getMessage();
    }
}
