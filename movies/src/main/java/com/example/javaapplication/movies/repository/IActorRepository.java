package com.example.javaapplication.movies.repository;

import com.example.javaapplication.movies.domain.Actor;

import java.util.List;

public interface IActorRepository {

    Actor get(Long actorsId);
    Actor add(Long movieId, Actor actors);
    Actor update(Long actorsId, Actor actorsToBeAdded, Long movieId);
    void delete(Long actorsId);
    List<Actor> getAll();
    List<Actor> getAllByMoviesId(Long id);
    void deleteAllByMoviesId(Long id);
}
