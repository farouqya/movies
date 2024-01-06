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

    @Override
    public Movies mapRow(ResultSet rs, int rowNum) throws SQLException {
        BeanPropertyRowMapper<Movies> rowMapper = new BeanPropertyRowMapper<>(Movies.class);
        Movies movies = rowMapper.mapRow(rs, rowNum);

        PGInterval pgInterval = (PGInterval) rs.getObject("length");
        movies.setLength(pgInterval);
        return movies;
    }
}
