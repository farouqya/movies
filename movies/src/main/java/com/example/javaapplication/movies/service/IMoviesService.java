package com.example.javaapplication.movies.service;

import com.example.javaapplication.movies.domain.Movies;

import java.util.Map;

public interface IMoviesService {

    Movies get(Long id);
    Movies add(Movies movies);
    Movies update(Long id, Movies movies);
    Movies patchUpdate(Long movieId, Map<Object, Object> updates);
    void delete(Long id);
}
