package com.example.javaapplication.movies.appevents;

import com.example.javaapplication.movies.domain.Actor;
import com.example.javaapplication.movies.domain.Genre;
import com.example.javaapplication.movies.domain.Movies;
import com.example.javaapplication.movies.repository.IActorRepository;
import com.example.javaapplication.movies.repository.IMoviesRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class RepositoryExample {

    private IMoviesRepository iMoviesRepository;
    private IActorRepository iActorRepository;

    public RepositoryExample(IMoviesRepository iMoviesRepository, IActorRepository iActorRepository) {
        this.iMoviesRepository = iMoviesRepository;
        this.iActorRepository = iActorRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void moviesRepositoryPlayground() {
        Movies movies = iMoviesRepository.get(5L);
        System.out.println(movies);

        // To add Movies -->
        Movies moviesToBeAddedToDatabase = new Movies("Run for the Money", 2022, Genre.ACTION, null, "Win Morisaki", 7.2, Duration.ofHours(1));
        Movies justAddedMovies = iMoviesRepository.add(moviesToBeAddedToDatabase);
        System.out.println("justAddedMovies : " + justAddedMovies);

        // to update Movies -->

        Movies moviesToBeUpdatedToDatabase = new Movies("Run for the Money", 2022, Genre.SCIFIC, null, "Win Morisaki", 7.2, Duration.ofHours(1));
        Movies justUpdatedMovies = iMoviesRepository.update(9L, moviesToBeUpdatedToDatabase);
        System.out.println("justUpdatedMovies : " + justUpdatedMovies);

        // to delete Movies -->

         iMoviesRepository.delete(9L);

        // to get all Movies..
        List<Movies> moviesList = iMoviesRepository.getAll();
        System.out.println(moviesList);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void actorsRepositoryPlayground() {
        Actor actor = iActorRepository.get(5L);
        System.out.println(actor);

        // To add Actors -->
        Actor actorsToBeAdded = new Actor("Ed Harris", 73, "Male", "American");
        Actor justAddedActors = iActorRepository.add(8L, actorsToBeAdded);
        System.out.println("justAddedMovies : " + justAddedActors);

        // to update Actors -->

        Actor actorsToBeUpdated = new Actor("Ed Harris", 73, "Male", "American");
        Actor justUpdatedActors = iActorRepository.update(15L, actorsToBeUpdated,2L);
        System.out.println("justUpdatedActors : " + justUpdatedActors);

        // to delete Actor -->

        iActorRepository.delete(9L);

        // to get all Actors..
        List<Actor> actorList = iActorRepository.getAll();
        System.out.println(actorList);
    }
}
