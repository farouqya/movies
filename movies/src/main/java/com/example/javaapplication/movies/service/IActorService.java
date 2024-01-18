package com.example.javaapplication.movies.service;

import com.example.javaapplication.movies.domain.Actor;

import java.util.Map;

public interface IActorService {

    Actor get(Long actorId);
    Actor add(Long movieId, Actor actorsTobeAdded);
    Actor update(Long actorsId, Actor actorsToBeAdded, Long movieId);
    Actor patchUpdate(Long actorsId, Map<Object, Object> updates, Long movieID);
    void delete(Long actorId);

}
