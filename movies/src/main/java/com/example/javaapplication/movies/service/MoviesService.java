package com.example.javaapplication.movies.service;

import com.example.javaapplication.movies.domain.Actor;
import com.example.javaapplication.movies.domain.Movies;
import com.example.javaapplication.movies.exceptions.ArgumentException;
import com.example.javaapplication.movies.exceptions.ResourceIsNotFoundException;
import com.example.javaapplication.movies.repository.IActorRepository;
import com.example.javaapplication.movies.repository.IMoviesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoviesService implements IMoviesService {

    private final IMoviesRepository moviesRepository;
    private final IActorRepository actorRepository;

    public MoviesService(IMoviesRepository moviesRepository,
                         IActorRepository actorRepository) {
        this.moviesRepository = moviesRepository;
        this.actorRepository = actorRepository;
    }

    @Override
    public Movies get(Long id) {
        Movies movies = moviesRepository.get(id);
        if (movies == null) {
            throw new ResourceIsNotFoundException("Movie with id " + id + " is not found");
        }
        List<Actor> actors = actorRepository.getAllByMoviesId(id);
        movies.setActors(actors);
        return movies;
    }

    @Override
    public Movies add(Movies movies) {
        if (movies.getYear() <= 0 ) {
            throw new ArgumentException("Year must be a positive number!! ");
        }
        if (movies.getRating() <= 0 ) {
            throw new ArgumentException("Rating must be a Zero Or a positive number!! ");
        }

        if (movies.getTitle() != null && movies.getGenre() != null) {
            throw new ResourceIsNotFoundException(" ((Name and Genre)) should not be null!!");
        }

        Movies addedMovies = moviesRepository.add(movies);
        Long addedMoviesId = addedMovies.getId();// primary Key
        movies.getActors().forEach(actors -> actorRepository.add(addedMoviesId,actors)); // ForeignID Key..
        return get(addedMoviesId);
    }

    @Override
    public Movies update(Long movieId, Movies movies) {

        get(movieId);
        Movies updatedMovies = moviesRepository.update(movieId, movies);
        actorRepository.deleteAllByMoviesId(movieId);
        movies.getActors().forEach(actor -> actorRepository.add(movieId,actor));
        return  get(movieId);
    }

    @Override
    public void delete(Long id) {
        get(id);
        actorRepository.deleteAllByMoviesId(id);
        moviesRepository.delete(id);
    }
}
