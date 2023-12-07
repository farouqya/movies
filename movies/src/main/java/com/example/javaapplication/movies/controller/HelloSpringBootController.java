package com.example.javaapplication.movies.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// for testing only.. to make sure that the application is working.. and it is working :)))
@RequestMapping("/hellopath")
@RestController
public class HelloSpringBootController {

    @GetMapping
    public String hello() {
        return "hello from spring boot app";
    }
}
