package com.example.javaapplication.movies.appevents;

import com.example.javaapplication.movies.domain.Actor;
import com.example.javaapplication.movies.service.IActorService;
import com.example.javaapplication.movies.service.IMoviesService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.Arrays;
import java.util.List;

//@Component
public class ServiceExamples {

    private IMoviesService moviesService;
    private IActorService actorService;

    public ServiceExamples(IMoviesService moviesService, IActorService actorService) {
        this.moviesService = moviesService;
        this.actorService = actorService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void movieServicePlayground() {

        //Movies moviesToBeAddedToDatabase = new Movies("Run for the Money", 2022, Genre.ACTION, null, "Win Morisaki", 7.2, Duration.ofHours(1));
        //Movies movie = moviesService.get(10L);
//        System.out.println(movie.getActors());
//        System.out.println(movie);
//
//        List<Actor> actorsToBeAdded = Arrays.asList( new Actor("Mai Whelan", 56, "Female", "American"));
//
//        Movies moviesToBeAdded = new Movies("Squid Game: The Challenge",2021, "Action",actorsToBeAdded, "Leanne Tear", 5.8, Duration.ofHours(1));
//        Movies updatedMoviesWithActors = moviesService.update(21L,moviesToBeAdded);
//        System.out.println(updatedMoviesWithActors);

//        Movies movie2 = moviesService.get(11L);
//        System.out.println(movie2.getActors());
//        System.out.println(movie2);

//        moviesService.delete(15L);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void actorServicePlayground() {

        Actor actor = actorService.get(2L);
        System.out.println("get : " + actor);

        List<Actor> actorsList= Arrays.asList( new Actor("Mai ", 56, "male", "American"));
        Actor actor10 = new Actor("Jerry11", 30, "mouse", "American");
        Actor actorsToBeAdded = actorService.add(5L, actor10);
        System.out.println("actorsToBeAdded : " + actorsToBeAdded);
//        Actor toUpdateActors = actorService.update(20L,actor1,100L);
//        System.out.println(toUpdateActors);

        //actorService.delete(17L);
    }
}
