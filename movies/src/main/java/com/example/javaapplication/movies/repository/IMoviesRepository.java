package com.example.javaapplication.movies.repository;

import com.example.javaapplication.movies.domain.Movies;

import java.util.List;

public interface IMoviesRepository {

    Movies get(Long movieId);
    Movies add(Movies movies);
    Movies update(Long movieId, Movies updatedMovies);
    void delete(Long movieId);
    List<Movies> getAll();
    //List<Movies> getAllMoviesByActorId(Long id)
}
