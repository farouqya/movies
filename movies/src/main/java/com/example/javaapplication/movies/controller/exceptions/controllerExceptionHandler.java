package com.example.javaapplication.movies.controller.exceptions;

import com.example.javaapplication.movies.exceptions.ArgumentException;
import com.example.javaapplication.movies.exceptions.ResourceIsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class controllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler({ResourceIsNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleResourceNotFoundException(ResourceIsNotFoundException exception) {
        return new ErrorMessage(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler({ArgumentException.class})
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorMessage handleArgumentException(ArgumentException exception) {
        return new ErrorMessage(exception.getMessage());
    }
}
