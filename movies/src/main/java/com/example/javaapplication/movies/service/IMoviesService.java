package com.example.javaapplication.movies.service;

import com.example.javaapplication.movies.domain.Movies;

public interface IMoviesService {

    Movies get(Long id);
    Movies add(Movies movies);
    Movies update(Long id, Movies movies);
    void delete(Long id);
}
