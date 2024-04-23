package com.example.javaapplication.movies.service;

import com.example.javaapplication.movies.domain.Actor;
import com.example.javaapplication.movies.domain.Genre;
import com.example.javaapplication.movies.domain.Movies;
import com.example.javaapplication.movies.exceptions.ArgumentException;
import com.example.javaapplication.movies.exceptions.ResourceIsNotFoundException;
import com.example.javaapplication.movies.jms.JmsMoviesSender;
import com.example.javaapplication.movies.repository.ActorRepo;
import com.example.javaapplication.movies.repository.IActorRepository;
import com.example.javaapplication.movies.repository.IMoviesRepository;
import com.example.javaapplication.movies.repository.MoviesRepo;
import com.example.javaapplication.movies.util.PostgresUtil;
import jakarta.transaction.Transactional;
import org.postgresql.util.PGInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class MoviesService implements IMoviesService {

    // Old Method -->
    private final IMoviesRepository moviesRepository;
    private final IActorRepository actorRepository;

    // Hibernate method -->
    private final MoviesRepo moviesRepo;
    private final ActorRepo actorRepo;

    //ActiveMq sender -->
    private final JmsMoviesSender moviesSender;

    @Autowired
    public MoviesService(IMoviesRepository moviesRepository,
                         IActorRepository actorRepository,
                         MoviesRepo moviesRepo, ActorRepo actorRepo, JmsMoviesSender moviesSender) {
        this.moviesRepository = moviesRepository;
        this.actorRepository = actorRepository;
        this.moviesRepo = moviesRepo;
        this.actorRepo = actorRepo;
        this.moviesSender = moviesSender;
    }

    @Override
    @Transactional
    public Movies get(Long id) {

        // Old method -->
//        Movies movies = moviesRepository.get(id);
//        if (movies == null) {
//            throw new ResourceIsNotFoundException("Movie with id " + id + " is not found");
//        }
//        List<Actor> actors = actorRepository.getAllByMoviesId(id);
//        movies.setActors(actors);
//        return movies;

        // Via Hibernate code -->
        Movies movies = moviesRepo.findById(id)
                .orElseThrow(() -> new ResourceIsNotFoundException("Movie with id " + id + " is not found"));
        movies.setActors(actorRepo.findBymovieTableId(id));
        return movies;
    }

    @Override
    @Transactional
    public List<Movies> getALl() {
        List<Movies> listMovies = moviesRepository.getAll();
        if (listMovies == null) {
            throw new ResourceIsNotFoundException("No movies exist!!");
        }

        // Old Way -->
//        List<Actor> actorList = actorRepository.getAll();
//        listMovies.forEach(movies -> movies.setActors(actorList));
//        return listMovies;

        // Via Hibernate -->
        List<Movies> movies = moviesRepo.findAll();
        for (Movies movie : movies) {
            movie.setActors(actorRepo.findAll());
        }
        return movies;
    }

    @Override
    @Transactional
    public Movies add(Movies movies) {
        if (movies.getYear() <= 0) {
            throw new ArgumentException("Year must be a positive number!! ");
        }
        if (movies.getRating() <= 0) {
            throw new ArgumentException("Rating must be a Zero Or a positive number!! ");
        }
        if (movies.getTitle() == null || movies.getGenre() == null) {
            throw new ResourceIsNotFoundException("((Name and Genre)) should not be null!!");
        }

        // Old Way -->
//        Movies addedMovies = moviesRepository.add(movies);
//        Long addedMoviesId = addedMovies.getMovieId();// primary Key
//        if(movies.getActors() != null) {
//            movies.getActors().forEach(actors -> actorRepository.add(addedMoviesId, actors)); // ForeignID Key..
//        }
//
//        return get(addedMoviesId);

        // Via Hibernate -->
        Movies addedMovies = moviesRepo.save(movies);
        List<Actor> actorList = movies.getActors();
        for (Actor actor: actorList) {
            actor.setMovieTableId(movies.getMovieId());
        }
        actorRepo.saveAll(movies.getActors());
        moviesSender.sendMoviesAddedMessage(addedMovies);
        return addedMovies;
    }

    @Override
    @Transactional
    public Movies update(Long movieId, Movies movies) {

        get(movieId);
        movies.setMovieId(movieId);
//        Movies updatedMovies = moviesRepository.update(movieId, movies);
//        actorRepository.deleteAllByMoviesId(movieId);
//
//        if (movies.getActors() != null) {
//            movies.getActors().forEach(actor -> actorRepository.add(movieId, actor));
//        }
//        return get(movieId);

        Movies addedMovies = moviesRepo.save(movies);
        List<Actor> actorList = movies.getActors();

        if (actorList != null) {
            for (Actor actor : actorList) {
                actor.setMovieTableId(movieId);
                actorRepo.saveAll(movies.getActors());
            }
        }
        return addedMovies;
    }

    @Override
    @Transactional
    public Movies patchUpdate(Long movieId, Map<Object, Object> updates) {
        Movies movies1 = get(movieId);

        updates.forEach((key, value) -> {

            try {
                if ("title".equals(key)) {
                    movies1.setTitle((String) value);
                } else if ("year".equals(key)) {
                    if ((Integer) value < 0) {
                        throw new ArgumentException("Year has to be valid");
                    } else {
                        movies1.setYear((Integer) value);
                    }
                } else if ("genre".equals(key)) {
                    movies1.setGenre(Genre.valueOf((String) value));
                } else if ("actors".equals(key)) {
                    if (value instanceof List<?> listValue) {
                        List<Actor> actorTobeAdded = listValue.stream()
                                .peek(element -> {
                                    if (element instanceof Map) {
                                        System.out.println("Actor element: " + element.getClass());
                                    }
                                })
                                .filter(Map.class::isInstance)
                                .map(Map.class::cast)
                                .filter(map -> map.containsKey("name") && map.containsKey("age") && map.containsKey("gender") && map.containsKey("nationality"))
                                .map(map -> new Actor((String) map.get("name"), (int) map.get("age"), (String) map.get("gender"), (String) map.get("nationality")))
                                .collect(Collectors.toList());

                        List<Actor> actorsExisted = movies1.getActors();
                        List<Actor> actorsList = new ArrayList<>(actorsExisted);
                        actorsList.addAll(actorTobeAdded);

                        try {
                            for (Actor actors : actorsList) {
                                actorRepository.add(movieId, actors);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else if ("director".equals(key)) {
                    movies1.setDirector((String) value);
                } else if ("rating".equals(key)) {
                    if ((double) value < 0) {
                        throw new ArgumentException("Rating should have a non-negative number");
                    } else {
                        double settedRating = (double) value;
                        movies1.setRating(settedRating);
                    }
                } else if ("length".equals(key)) {
                    try {
                        PGInterval pgInterval = PostgresUtil.toPGInterval((String) value);
                        movies1.setLength(pgInterval);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    ;
                }
            } catch (ClassCastException e) {
                throw new ArgumentException("PLEASE make sure to enter these values for instance as legitimate values :" +
                        "title, genre, director" + " --> " + "Should have names only" +
                        "year should be valid number" + "Rating should have a value such as ((15)).. thanks");
            }

        });
        Movies updatedMovies = moviesRepository.update(movieId, movies1);
        return get(movieId);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        get(id);

        // Old way
//        actorRepository.deleteAllByMoviesId(id);
//        moviesRepository.delete(id);

        //Via Hibernate Way -->
        actorRepo.deleteBymovieTableId(id);
        moviesRepo.deleteById(id);
    }
}