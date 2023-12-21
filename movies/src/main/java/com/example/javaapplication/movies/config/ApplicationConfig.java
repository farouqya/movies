package com.example.javaapplication.movies.config;

// This class is class that store meta information how spring init "bean"

import com.example.javaapplication.movies.domain.Actor;
import com.example.javaapplication.movies.domain.Movies;
import org.postgresql.util.PGInterval;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
public class ApplicationConfig implements RowMapper<Movies> {

    // Custom RowMapper because converting from "PGInterval --> To --> Duration" results in exception in Regular Way..
    @Override
    public Movies mapRow(ResultSet rs, int rowNum) throws SQLException {
        BeanPropertyRowMapper<Movies> rowMapper = new BeanPropertyRowMapper<>(Movies.class);
        Movies movies = rowMapper.mapRow(rs, rowNum);


        //Fetch PGInterval directly from the result set -->

        PGInterval pgInterval = (PGInterval) rs.getObject("length");

        // Set Duration using the custom setter method -->

        movies.setLength(pgInterval);
        return movies;
    }

    @Bean
    public RowMapper<Actor> actorRowMapper() {
        return new BeanPropertyRowMapper<>(Actor.class);
    }
}
