package com.example.javaapplication.movies.repository;

import com.example.javaapplication.movies.domain.Actor;

import java.util.List;

public interface IActorRepository {

    Actor get(Long actorsId); // get Movie by Movie related table id..
    Actor add(Long movieId, Actor actors);
    Actor update(Long actorsId, Actor actorsToBeAdded, Long movieId);
    void delete(Long actorsId);
    List<Actor> getAll(); // to have duplication, we use List..
}
