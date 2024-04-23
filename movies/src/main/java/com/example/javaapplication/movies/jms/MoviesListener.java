package com.example.javaapplication.movies.jms;

import com.example.javaapplication.movies.domain.Movies;
import com.example.javaapplication.movies.exceptions.ResourceIsNotFoundException;
import com.example.javaapplication.movies.service.IMoviesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class MoviesListener {

    private final ObjectMapper objectMapper;
    private final IMoviesService moviesService;

    public MoviesListener(ObjectMapper objectMapper, IMoviesService moviesService) {
        this.objectMapper = objectMapper;
        this.moviesService = moviesService;
    }

    @JmsListener(destination = "movies")
    public void listenMovies(Message<String> message) {
        String payload = message.getPayload();

        try {
            JmsMoviesUpdateTitle moviesPayload = objectMapper.readValue(payload,JmsMoviesUpdateTitle.class);
            Movies existingMovies = moviesService.get(moviesPayload.getId());
            existingMovies.setTitle(moviesPayload.getTitle());
            moviesService.update(moviesPayload.getId(),existingMovies);

        } catch (ResourceIsNotFoundException e) {
            System.out.println("ResourceIsNotFoundException : " + e.getMessage());
        }
        catch (JsonProcessingException e) {
            System.out.println("Message is not JSON. Details: " + e.getMessage());
        }

        System.out.println("Message from JMS " + payload);
    }
}
