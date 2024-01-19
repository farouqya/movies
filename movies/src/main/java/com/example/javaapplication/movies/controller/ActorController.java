package com.example.javaapplication.movies.controller;

import com.example.javaapplication.movies.domain.Actor;
import com.example.javaapplication.movies.service.IActorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api/actors")
@RestController
public class ActorController {

    private final IActorService actorService;

    public ActorController(IActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping(path="/{actorsId}")
    public Actor getOne(@PathVariable Long actorsId) {
        return actorService.get(actorsId);
    }

    @PostMapping(path="/{movieId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Actor add(@PathVariable Long movieId, @RequestBody Actor actors){
        return actorService.add(movieId,actors);
    }

    @PutMapping(path="/{movieId}/{actorsId}")
    public Actor update(@PathVariable Long actorsId, @RequestBody Actor actorsToBeAdded, @PathVariable Long movieId) {
        return actorService.update(actorsId,actorsToBeAdded,movieId);
    }

    @PatchMapping(path="/{movieId}/{actorsId}")
    public Actor patchUpdate(@PathVariable Long actorsId, @RequestBody Map<Object, Object> updates, @PathVariable Long movieId) {
        return actorService.patchUpdate(actorsId,updates,movieId);
    }

    @DeleteMapping(path="/{actorsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long actorsId) {
        actorService.delete(actorsId);
    }
}
