package com.example.javaapplication.movies.service;

import com.example.javaapplication.movies.domain.Actor;
import com.example.javaapplication.movies.domain.Movies;
import com.example.javaapplication.movies.exceptions.ArgumentException;
import com.example.javaapplication.movies.exceptions.ResourceIsNotFoundException;
import com.example.javaapplication.movies.repository.ActorRepo;
import com.example.javaapplication.movies.repository.IActorRepository;
import com.example.javaapplication.movies.repository.IMoviesRepository;
import com.example.javaapplication.movies.repository.MoviesRepo;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Map;

public class ActorService implements IActorService {

    private final IMoviesRepository moviesRepository;
    private final IActorRepository actorRepository;
    private final IMoviesService moviesService;

    // Hibernate method -->
    private final MoviesRepo moviesRepo;
    private final ActorRepo actorRepo;

    public ActorService(IMoviesRepository moviesRepository, IActorRepository actorRepository, IMoviesService moviesService, MoviesRepo moviesRepo, ActorRepo actorRepo) {
        this.moviesRepository = moviesRepository;
        this.actorRepository = actorRepository;
        this.moviesService = moviesService;
        this.moviesRepo = moviesRepo;
        this.actorRepo = actorRepo;
    }

    @Override
    @Transactional
    public Actor get(Long actorId) {
//        Actor actor = actorRepository.get(actorId);

//        if (actor == null) {
//            throw new ArgumentException("Actor with id " + actorId + " is not found");
//        }
//        return actor;

        return actorRepo.findById(actorId)
                .orElseThrow(() -> new ResourceIsNotFoundException("Actor with id " + actorId + " is not found"));
    }

    @Override
    @Transactional
    public List<Actor> getAll() {
//        return actorRepository.getAll();
        return actorRepo.findAll();
    }

    @Override
    @Transactional
    public Actor add(Long movieId, Actor actor) {
        if (actor.getAge() < 0) {
            throw new ArgumentException("Age should not have a negative value !! Please check..");
        }
        if (actor.getName() == null) {
            throw new ResourceIsNotFoundException("Name is a must parameter!!");
        }


//        Movies movies = moviesService.get(movieId);
//        Actor actorsToBeAdded = actorRepository.add(movieId, actor);
//        Long addedActorsId = actorsToBeAdded.getId();
//        return get(addedActorsId);

        if (moviesRepo.findById(movieId).isPresent()) {
            actor.setMovieTableId(movieId);
            return actorRepo.save(actor);
        }
        throw new ResourceIsNotFoundException("Movie with id " + movieId + " is not found");
    }

    @Override
    public Actor update(Long actorsId, Actor actorsToBeAdded, Long movieId) throws ResourceIsNotFoundException {
        get(actorsId);
        Movies movies = moviesService.get(movieId);
//        if (movies != null) {
//            Actor updatedActor = actorRepository.update(actorsId, actorsToBeAdded, movieId);
//            return get(actorsId);
//        } else {
//            throw new ResourceIsNotFoundException("Movie with id "  + movieId + " is not found");
//        }

        actorsToBeAdded.setMovieTableId(movieId);
        return actorRepo.save(actorsToBeAdded);
    }

    @Override
    public Actor patchUpdate(Long movieId, Long actorsId, Map<Object, Object> updates) {
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
    @Transactional
    public void delete(Long actorId) {
        get(actorId);
//        actorRepository.delete(actorId);
        actorRepo.deleteById(actorId);
    }
}
