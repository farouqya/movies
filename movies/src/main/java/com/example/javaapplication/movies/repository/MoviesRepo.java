package com.example.javaapplication.movies.repository;

import com.example.javaapplication.movies.domain.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviesRepo extends JpaRepository<Movies, Long> {

}
