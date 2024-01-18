package com.example.javaapplication.movies.config;

import com.example.javaapplication.movies.domain.Movies;
import org.postgresql.util.PGInterval;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MoviesRowMapper implements RowMapper<Movies> {

    private final BeanPropertyRowMapper<Movies> rowMapper = new BeanPropertyRowMapper<>(Movies.class);

    @Override
    public Movies mapRow(ResultSet rs, int rowNum) throws SQLException {

        Movies movies = new Movies();
        PGInterval pgInterval = (PGInterval) rs.getObject("length");
        movies.setLength(pgInterval);
        movies = rowMapper.mapRow(rs, rowNum);
        Long movieId = rs.getLong("movie_id");
        movies.setId(movieId);
        return movies;
    }
}
