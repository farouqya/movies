package com.example.javaapplication.movies.service;

import com.example.javaapplication.movies.domain.Actor;

public interface IActorService {

    Actor get(Long actorId);
    Actor add(Long movieId, Actor actorsTobeAdded);
    Actor update(Long actorsId, Actor actorsToBeAdded, Long movieId);
    void delete(Long actorId);

}
