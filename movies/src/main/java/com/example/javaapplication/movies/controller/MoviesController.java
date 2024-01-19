package com.example.javaapplication.movies.controller;

import com.example.javaapplication.movies.domain.Movies;
import com.example.javaapplication.movies.service.IMoviesService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api/movies")
@RestController
public class MoviesController {

    private final IMoviesService moviesService;

    public MoviesController(IMoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @GetMapping(path="/{moviesId}")
    public Movies getOne(@PathVariable Long moviesId) {
        return moviesService.get(moviesId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Movies add(@RequestBody Movies movies) {
        return moviesService.add(movies);
    }

    @PutMapping(path="/{movieId}")
    public Movies update(@PathVariable Long movieId, @RequestBody Movies movies) {
        return moviesService.update(movieId,movies);
    }

    @PatchMapping("/{movieId}")
    public Movies patchUpdate(@PathVariable Long movieId, @RequestBody Map<Object, Object> updates) {
        return moviesService.patchUpdate(movieId,updates);
    }

    @DeleteMapping(path="/{moviesId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long moviesId) {
        moviesService.delete(moviesId);
    }
}
