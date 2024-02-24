package com.example.javaapplication.movies.service;

import com.example.javaapplication.movies.domain.Actor;
import com.example.javaapplication.movies.domain.Movies;
import com.example.javaapplication.movies.exceptions.ResourceIsNotFoundException;
import com.example.javaapplication.movies.repository.IActorRepository;
import com.example.javaapplication.movies.repository.IMoviesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;

import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class MoviesServiceTest {

    @InjectMocks
    private MoviesService moviesService;

    @Mock
    private IMoviesRepository moviesRepository;
    @Mock
    private IActorRepository actorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getMoviesWithActors() {
        Mockito.when(moviesRepository.get(45L)).thenReturn(new Movies());
        Mockito.when(actorRepository.getAllByMoviesId(45L)).thenReturn(
                Arrays.asList(new Actor(),
                        new Actor())
        );

        Movies movies = moviesService.get(45L);

        assertNotNull(movies);
        assertNotNull(movies.getActors());
        assertEquals(2, movies.getActors().size());
    }

    @Test
    void getMoviesWithoutActors() {
        Mockito.when(moviesRepository.get(55L)).thenReturn(new Movies());
        Movies movies = moviesService.get(55L);

        assertNotNull(movies);
        assertNotNull(movies.getActors());
        assertEquals(0, movies.getActors().size());
    }

    @Test
    void getMoviesThrowResourceIsNotFoundException() {
        ResourceIsNotFoundException resourceIsNotFoundException =
                assertThrowsExactly(ResourceIsNotFoundException.class, ()-> moviesService.get(55L));

            assertEquals("Movie with id 55 is not found",resourceIsNotFoundException.getMessage());
    }


    @Test
    void addMoviesWithActors() {
        Movies movie1 = new Movies("First Movie", 1999, "SCIFIC", null, "Win Morisaki", 7.2, Duration.ofHours(1));
        Movies movie2 = new Movies();
        Actor actor1 = new Actor("Jerry11", 30, "mouse", "American");
        Actor actor2 = new Actor("Jerry15", 21, "mouse", "American");
        Mockito.when(moviesRepository.add(any()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    Object firstArgument = invocation.getArgument(0);
                    Movies moviesArg = (Movies) firstArgument;
                    moviesArg.setId(445555L);
                    moviesArg.setActors(List.of(actor1, actor2));
                    return moviesArg;
                });

        Mockito.when(moviesRepository.get(445555L)).thenReturn(movie1);
        Mockito.when(actorRepository.add(445555L, actor1)).thenReturn(actor1);
        Mockito.when(actorRepository.add(445555L, actor2)).thenReturn(actor2);
        Mockito.when(actorRepository.getAllByMoviesId(445555L)).thenReturn(Arrays.asList(actor1, actor2));

        Movies addedMovie = moviesService.add(movie1);


        Mockito.verify(actorRepository).add(445555L, actor1);
        Mockito.verify(actorRepository).add(445555L, actor2);

        assertNotNull(addedMovie);
        assertNotNull(addedMovie.getId());
        assertNotNull(addedMovie.getActors());
        assertEquals(2,addedMovie.getActors().size());
    }

    @Test
    void addMoviesWithoutActors() {
        Movies movie1 = new Movies("First Movie", 1999, "SCIFIC", null, "Win Morisaki", 7.2, Duration.ofHours(1));
        Movies movie2 = new Movies();
        Mockito.when(moviesRepository.add(any()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    Object firstArgument = invocation.getArgument(0);
                    Movies moviesArg = (Movies) firstArgument;
                    moviesArg.setId(445555L);
                    return moviesArg;
                });

        Mockito.when(moviesRepository.get(445555L)).thenReturn(movie1);
        Movies addedMovie = moviesService.add(movie1);


        assertNotNull(addedMovie);
        assertNotNull(addedMovie.getId());
        assertNotNull(addedMovie.getActors());
        assertEquals(0,addedMovie.getActors().size());
    }

    @Test
    void addMoviesThrowResourceIsNotFoundException() {
        Movies movie1 = new Movies();
        movie1.setYear(2000);
        movie1.setRating(15.5);
        ResourceIsNotFoundException resourceIsNotFoundException =
                assertThrowsExactly(ResourceIsNotFoundException.class, ()-> moviesService.add(movie1));

        assertEquals("((Name and Genre)) should not be null!!",resourceIsNotFoundException.getMessage());
    }


    @Test
    void updateGetMoviesWithActors() {
        Movies movie1 = new Movies("First Movie", 1999, "SCIFIC", null, "Win Morisaki", 7.2, Duration.ofHours(1));
        Movies movie2 = new Movies("Second Movie", 1999, "ROMANCE", null, "Leee", 7.2, Duration.ofHours(1));
        Actor actor1 = new Actor("Jerry11", 30, "mouse", "American");
        Actor actor2 = new Actor("Jerry15", 21, "mouse", "American");

        Mockito.when(moviesRepository.get(45L)).thenReturn(movie1);
        Mockito.when(actorRepository.getAllByMoviesId(45L)).thenReturn(
                Arrays.asList(actor1, actor2));
        Mockito.when(moviesRepository.get(45L)).thenReturn(movie2);
        Mockito.when(moviesRepository.update(any(),any())).thenReturn(movie2);
        Movies updatedMovies = moviesService.update(45L,movie2);

        assertNotNull(updatedMovies);
        assertNotSame(updatedMovies, movie1);
        assertNotNull(updatedMovies.getActors());
        assertEquals(2, updatedMovies.getActors().size());
    }

    @Test
    void updateGetMoviesWithoutActors() {
        Movies movie1 = new Movies("First Movie", 1999, "SCIFIC", null, "Win Morisaki", 7.2, Duration.ofHours(1));
        Mockito.when(moviesRepository.get(55L)).thenReturn(movie1);
        Movies movie2 = new Movies("Second Movie", 1999, "ROMANCE", null, "Leee", 7.2, Duration.ofHours(1));
        Mockito.when(moviesRepository.get(55L)).thenReturn(movie2);
        Mockito.when(moviesRepository.update(any(),any())).thenReturn(movie2);
        Movies updatedMovies = moviesService.update(55L,movie2);

        assertNotNull(updatedMovies);
        assertNotSame(updatedMovies, movie1);
        assertNotNull(updatedMovies.getActors());
        assertEquals(0, updatedMovies.getActors().size());
    }

    @Test
    void patchUpdateGetMovie() {
        Mockito.when(moviesRepository.get(45L)).thenReturn(new Movies());
        Mockito.when(actorRepository.getAllByMoviesId(45L)).thenReturn(
                Arrays.asList(new Actor(),
                        new Actor())
        );

        Map<Object, Object> updates = Map.of("title", "Second Movie", "year", 1999, "genre", "ROMANCE",
                "actors", Arrays.asList(new Actor(), new Actor()), "director", "Lee", "rating", 7.2,
                "length", "PT9H56M");

        Movies movies = moviesService.patchUpdate(45L,updates);

        assertNotNull(movies);
        assertNotNull(movies.getActors());
        assertEquals(2, movies.getActors().size());
    }

    @Test
    void patchUpdateAllFields() {
        Movies movie1 = new Movies();
        Movies moviesUpdating = new Movies();
        Actor actor1 = new Actor();
        Actor actor2 = new Actor();
        Mockito.when(moviesRepository.get(45L)).thenReturn(movie1);
        Mockito.when(actorRepository.getAllByMoviesId(45L)).thenReturn(
                Arrays.asList(new Actor(),
                        new Actor())
        );
        Mockito.when(moviesRepository.get(45L)).thenReturn(moviesUpdating);

        Map<Object, Object> updates = Map.of("title", "Second Movie", "year", 1999, "genre", "ROMANCE",
                "actors", Arrays.asList(actor1, actor2), "director", "Lee", "rating", 7.2,
                "length", "PT9H56M");

         Movies patchUpdatedMovie = moviesService.patchUpdate(45L, updates);

        assertNotNull(patchUpdatedMovie);
        assertNotSame(patchUpdatedMovie, movie1);
        assertNotNull(patchUpdatedMovie.getActors());
        assertEquals(2, patchUpdatedMovie.getActors().size());
    }

    @Test
    void patchUpdateAField() {
        Movies movie1 = new Movies();
        Movies movie2 = new Movies();
        Actor actor1 = new Actor();
        Actor actor2 = new Actor();
        Mockito.when(moviesRepository.get(55L)).thenReturn(movie1);
        Mockito.when(actorRepository.getAllByMoviesId(55L)).thenReturn(
                Arrays.asList(new Actor(),
                        new Actor())
        );

        Mockito.when(moviesRepository.get(55L)).thenReturn(movie2);
        Mockito.when(moviesRepository.update(any(),any())).thenReturn(movie2);
        Map<Object, Object> updates = new LinkedHashMap<>();
        updates = Map.of("length", "PT10H56M");

        Movies patchUpdatedMovie = moviesService.patchUpdate(55L, updates);

        assertNotNull(patchUpdatedMovie);
        assertNotSame(patchUpdatedMovie, movie1);
        assertNotNull(patchUpdatedMovie.getActors());
        assertEquals(2, patchUpdatedMovie.getActors().size());
    }

    @Test
    void patchUpdateActorField() {
        Movies movie1 = new Movies();
        Movies movie2 = new Movies();
        Actor actor1 = new Actor("Jerry11", 30, "mouse1", "American");
        Actor actor2 = new Actor("Jerry15", 21, "mouse2", "American");
        Actor actor3 = new Actor("Jerry12", 22, "mouse3", "American");
        Actor actor4 = new Actor("Jerry20", 25, "mouse4", "American");

        Mockito.when(moviesRepository.get(50L)).thenReturn(movie1);
        Mockito.when(actorRepository.getAllByMoviesId(50L)).thenReturn(
                Arrays.asList(actor3, actor4));

        Mockito.when(moviesRepository.get(55L)).thenReturn(movie2);
        Mockito.when(actorRepository.getAllByMoviesId(55L)).thenReturn(Arrays.asList(actor1,actor2));
        Map<Object, Object> updates = Map.of("actors", Arrays.asList(actor1,actor2));

        Movies patchUpdatedMovie = moviesService.patchUpdate(55L, updates);
        System.out.println("New movie : " + patchUpdatedMovie);
        movie1 = moviesService.patchUpdate(50L,updates);
        System.out.println("movie1 : " + movie1);

        assertNotNull(patchUpdatedMovie);
        assertNotEquals(patchUpdatedMovie.getActors(),movie1.getActors());
        assertNotNull(patchUpdatedMovie.getActors());
        assertEquals(2, patchUpdatedMovie.getActors().size());
    }


    @Test
    void delete() {
        Mockito.when(moviesRepository.get(45L)).thenReturn(new Movies());
        Mockito.when(actorRepository.getAllByMoviesId(45L)).thenReturn(
                Arrays.asList(new Actor(),
                        new Actor())
        );

        moviesService.delete(45L);
        Mockito.verify(moviesRepository).delete(45L);
    }
}