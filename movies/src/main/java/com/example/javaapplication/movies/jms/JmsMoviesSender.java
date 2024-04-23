package com.example.javaapplication.movies.jms;

import com.example.javaapplication.movies.domain.Movies;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsMoviesSender {

    private static final String MOVIES_NOTIFIER_ADD = "movies-notifier-add";
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public JmsMoviesSender(JmsTemplate jmsTemplate, ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMoviesAddedMessage(Movies addedMovies) {
        try {
            jmsTemplate.convertAndSend(MOVIES_NOTIFIER_ADD, objectMapper.writeValueAsString(addedMovies));
        } catch (JsonProcessingException e) {
            System.out.println("Something went wrong during converting");
        }
    }
}
