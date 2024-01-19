package com.example.javaapplication.movies.config;

// This class is class that store meta information how spring init "bean"

import com.example.javaapplication.movies.domain.Actor;
import com.example.javaapplication.movies.repository.IActorRepository;
import com.example.javaapplication.movies.repository.IMoviesRepository;
import com.example.javaapplication.movies.service.ActorService;
import com.example.javaapplication.movies.service.IActorService;
import com.example.javaapplication.movies.service.IMoviesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

@Configuration
public class ApplicationConfig {

    @Bean
    public RowMapper<Actor> actorRowMapper() {
        return new BeanPropertyRowMapper<>(Actor.class);
    }

    @Bean
    public IActorService actorService(IMoviesRepository moviesRepository, IActorRepository actorRepository, IMoviesService moviesService) {
        return new ActorService(moviesRepository,actorRepository, moviesService);
    }
}
