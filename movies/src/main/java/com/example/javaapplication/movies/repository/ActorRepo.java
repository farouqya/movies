package com.example.javaapplication.movies.repository;

import com.example.javaapplication.movies.domain.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepo extends JpaRepository<Actor, Long> {

    List<Actor> findBymovieTableId(Long id);
//    List<Actor> saveAllBymovieTableId(Long id, List<Actor> actorList);
    void deleteBymovieTableId(Long id);
}
