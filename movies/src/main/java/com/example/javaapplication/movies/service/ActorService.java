package com.example.javaapplication.movies.service;

import com.example.javaapplication.movies.domain.Actor;
import com.example.javaapplication.movies.domain.Movies;
import com.example.javaapplication.movies.exceptions.ArgumentException;
import com.example.javaapplication.movies.exceptions.ResourceIsNotFoundException;
import com.example.javaapplication.movies.repository.IActorRepository;
import com.example.javaapplication.movies.repository.IMoviesRepository;
import java.util.Map;

public class ActorService implements IActorService {

    private final IMoviesRepository moviesRepository;
    private final IActorRepository actorRepository;
    private final IMoviesService moviesService;

    public ActorService(IMoviesRepository moviesRepository, IActorRepository actorRepository, IMoviesService moviesService) {
        this.moviesRepository = moviesRepository;
        this.actorRepository = actorRepository;
        this.moviesService = moviesService;
    }

    @Override
    public Actor get(Long actorId) {
        Actor actor = actorRepository.get(actorId);
        if (actor == null) {
            throw new ArgumentException("Actor with id " + actorId + " is not found");
        }
        return actor;
    }

    @Override
    public Actor add(Long movieId, Actor actor) {
        if (actor.getAge() < 0) {
            throw new ArgumentException("Age should not have a negative value !! Please check..");
        }
        if (actor.getName() == null) {
            throw new ResourceIsNotFoundException("Name is a must parameter!!");
        }

        Movies movies = moviesService.get(movieId);
        Actor actorsToBeAdded = actorRepository.add(movieId, actor);
        Long addedActorsId = actorsToBeAdded.getId();
        return get(addedActorsId);
    }

    @Override
    public Actor update(Long actorsId, Actor actorsToBeAdded, Long movieId) {
        get(actorsId);
        Movies movies = moviesService.get(movieId);
        Actor updatedActor = actorRepository.update(actorsId, actorsToBeAdded, movieId);
        return get(actorsId);
    }

    @Override
    public Actor patchUpdate(Long actorsId, Map<Object, Object> updates, Long movieId) {
        Actor actor = get(actorsId);
        Movies movies = moviesService.get(movieId);

        updates.forEach((key, value) -> {

            try {
                if ("id".equals(key)) {
                    actor.setId((Long) value);
                }
                if ("name".equals(key)) {
                    actor.setName((String) value);
                } else if ("age".equals(key)) {
                    if ((Integer) value < 0) {
                        throw new ArgumentException("Age has to be valid");
                    } else {
                        actor.setAge((Integer) value);
                    }
                } else if ("gender".equals(key)) {
                    actor.setGender((String) value);
                } else if ("nationality".equals(key)) {
                    actor.setNationality((String) value);
                }
            } catch (ClassCastException e) {
                throw new ArgumentException("Please verify that name, gender, and nationality to have strings values only" +
                        "In addition, age to have a valid number as well and to be Integer Number!!");
            }
        });

        return actorRepository.update(actorsId, actor, movieId);
    }

    @Override
    public void delete(Long actorId) {
        get(actorId);
        actorRepository.delete(actorId);
    }
}
