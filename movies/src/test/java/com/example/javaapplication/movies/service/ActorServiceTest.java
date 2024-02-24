package com.example.javaapplication.movies.service;

import com.example.javaapplication.movies.domain.Actor;
import com.example.javaapplication.movies.domain.Movies;
import com.example.javaapplication.movies.exceptions.ArgumentException;
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
import org.mockito.stubbing.Answer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class ActorServiceTest {

    @InjectMocks
    private ActorService actorService;

    @Mock
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
    void getActors() {
        Mockito.when(actorRepository.get(55L)).thenReturn(new Actor());
        Actor newActor = actorService.get(55L);
        assertNotNull(newActor);
    }

    @Test
    void getActorsThrowsArgumentException() {
        ArgumentException argumentException =
                assertThrowsExactly(ArgumentException.class, ()-> actorService.get(55L));

        assertEquals("Actor with id 55 is not found",argumentException.getMessage());
    }

    @Test
    void addActors() {
        Movies movie1 = new Movies("First Movie", 1999, "SCIFIC", null, "Win Morisaki", 7.2, Duration.ofHours(1));
        Movies movie2 = new Movies();
        Actor actor1 = new Actor("Jerry11", 30, "mouse", "American");
        Actor actor2 = new Actor("Jerry15", 21, "mouse", "American");

        Mockito.when(actorRepository.add(any(),any()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    Object firstArgument = invocation.getArgument(0);
                    Long movieArg = (Long) firstArgument;
                    Movies movie = new Movies();
                    movie.setId(445555L);
                    Object secondArgument = invocation.getArgument(1);
                    Actor actorArg = (Actor) secondArgument;
                    actorArg.setId(55L);
                    return actorArg;
                });

        Mockito.when(moviesService.get(445555L)).thenReturn(movie1);
        Mockito.when(actorRepository.get(55L)).thenReturn(actor2);
        Mockito.when(actorRepository.getAllByMoviesId(445555L)).thenReturn(Arrays.asList(actor1, actor2));
        Actor addedActor = actorService.add(445555L, actor2);


        assertNotNull(addedActor);
        assertNotNull(addedActor.getId());
        assertEquals(55L, addedActor.getId());
    }

    @Test
    void addActorsThrowsArgumentException() {
        Actor actor2 = new Actor("Baggio", -21, "mouse", "American");
        Mockito.when(actorRepository.add(any(),any())).thenReturn(actor2);

        Mockito.when(actorRepository.add(any(),any()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    Object firstArgument = invocation.getArgument(0);
                    Long movieArg = (Long) firstArgument;
                    Movies movie = new Movies();
                    movie.setId(445555L);
                    Object secondArgument = invocation.getArgument(1);
                    Actor actorArg = (Actor) secondArgument;
                    actorArg.setId(55L);
                    return actorArg;
                });

        Mockito.when(actorRepository.get(55L)).thenReturn(actor2);
        ArgumentException argumentException =
                assertThrowsExactly(ArgumentException.class, ()-> actorService.add(445555L,actor2));

        assertEquals("Age should not have a negative value !! Please check..",argumentException.getMessage());
    }

    @Test
    void addActorsThrowResourceIsNotFoundException() {

        Actor actor2 = new Actor(null, 21, "mouse", "American");
        Mockito.when(actorRepository.add(any(),any())).thenReturn(actor2);
        ResourceIsNotFoundException resourceIsNotFoundException =
                assertThrowsExactly(ResourceIsNotFoundException.class, ()-> actorService.add(55L, actor2));

        assertEquals("Name is a must parameter!!",resourceIsNotFoundException.getMessage());
    }

    @Test
    void updateWithMovieAndActor() {
        Movies movie1 = new Movies("First Movie", 1999, "SCIFIC", null, "Win Morisaki", 7.2, Duration.ofHours(1));
        Movies movie2 = new Movies();
        Actor actor1 = new Actor();
        Actor actor2 = new Actor("Baggio", -21, "mouse", "American");
        Mockito.when(actorRepository.get(55L)).thenReturn(actor1);
        Mockito.when(actorRepository.get(55L)).thenReturn(actor2);
        Mockito.when(moviesService.get(4456L)).thenReturn(movie1);
        Mockito.when(actorRepository.update(55L,actor1,4456L)).thenReturn(actor2);


        Actor updatedActor = actorService.update(55L,actor1,4456L);

        assertNotNull(updatedActor);
        assertNotNull(updatedActor.getName());
    }

    @Test
    void updateWithMovieNoActorThrowsArgumentException(){
        ArgumentException argumentException =
                assertThrowsExactly(ArgumentException.class, ()-> actorService.update(55L,new Actor(),4456L));

        assertEquals("Actor with id 55 is not found",argumentException.getMessage());
    }

    @Test
    void updateNoMovieWithActorThrowsResourceIsNotFoundException(){
        Movies movie1 = new Movies("First Movie", 1999, "SCIFIC", null, "Win Morisaki", 7.2, Duration.ofHours(1));
        Movies movie2 = new Movies();
        Actor actor1 = new Actor();
        Actor actor2 = new Actor("Baggio", -21, "mouse", "American");

        Mockito.when(actorRepository.get(55L)).thenReturn(actor1);

        ResourceIsNotFoundException resourceIsNotFoundException =
                assertThrowsExactly(ResourceIsNotFoundException.class, ()-> actorService.update(55L, actor2,4456L));

        assertEquals("Movie with id 4456 is not found",resourceIsNotFoundException.getMessage());
    }

    @Test
    void patchUpdateGetActors() {

        Map<Object, Object> updates = Map.of("id", 55L,"name", "Sandra", "age", 21, "gender", "Female","nationality","American");
        Actor actor2 = new Actor("Baggio", 21, "mouse", "American");
        Mockito.when(actorRepository.get(55L)).thenReturn(actor2);
        Mockito.when(actorRepository.update(55L,actor2,4456L)).thenReturn(actor2);
        Actor newActor = actorService.patchUpdate(4456L,55L,updates);
        assertNotNull(newActor);
    }

    @Test
    void patchUpdateAllFields() {

        Movies movie1 = new Movies();
        Movies moviesUpdating = new Movies();
        Actor actor1 = new Actor();
        Actor actor2 = new Actor();
        Mockito.when(actorRepository.get(55L)).thenReturn(actor1);
        Mockito.when(moviesRepository.get(45L)).thenReturn(moviesUpdating);

        Map<Object, Object> updates = Map.of("id", 55L,"name", "Sandra", "age", 21, "gender", "Female","nationality","American");
        Mockito.when(actorRepository.update(any(),any(),any())).thenReturn(actor2);

        Actor updatedActors = actorService.patchUpdate(45L,55L,updates);
        System.out.println(updatedActors + " " + actor1);

        assertNotNull(updatedActors);
        assertNotSame(updatedActors, actor1);
    }

    @Test
    void patchUpdateAFieldInActor() {

        Movies movie1 = new Movies();
        Movies moviesUpdating = new Movies();
        Actor actor1 = new Actor();
        Actor actor2 = new Actor("Sandra", 21, "Female", "American");
        Mockito.when(actorRepository.get(55L)).thenReturn(actor1);
        Mockito.when(moviesRepository.get(45L)).thenReturn(moviesUpdating);

        Map<Object, Object> updates = Map.of("name", "Sandraaa", "age", 21, "gender", "Female","nationality","American");
        Mockito.when(actorRepository.update(any(),any(),any())).thenReturn(actor1);

        Actor updatedActors = actorService.patchUpdate(45L,55L,updates);
        System.out.println(updatedActors + " " + actor2);

        assertNotNull(updatedActors);
        assertNotSame(updatedActors, actor2);

    }

    @Test
    void delete() {
         Actor actor1 = new Actor();
        Mockito.when(actorRepository.get(55L)).thenReturn(actor1);
        Mockito.doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) throws Throwable {

                System.out.println("Actor is deleted!!");
                return null;
            }}).when(actorRepository).delete(55L);

        actorService.delete(55L);
        Mockito.verify(actorRepository).delete(55L);
    }
}